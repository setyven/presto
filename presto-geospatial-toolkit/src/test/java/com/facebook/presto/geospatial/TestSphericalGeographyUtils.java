/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.geospatial;

import com.esri.core.geometry.Point;
import org.testng.annotations.Test;

public class TestSphericalGeographyUtils
{
    @Test
    public void testCrossProductPoint()
    {
        SphericalGeographyUtils.CartesianPoint p1 = new SphericalGeographyUtils.CartesianPoint(1, 2, 3);
        SphericalGeographyUtils.CartesianPoint p2 = new SphericalGeographyUtils.CartesianPoint(4, 5, 6);
        SphericalGeographyUtils.CartesianPoint cross = p1.crossProduct(p2);
        assert cross.getX() == -3;
        assert cross.getY() == 6;
        assert cross.getZ() == -3;
    }

    @Test
    public void testCartesianPointToPoint()
    {
        Point p = new Point(45, 32);
        SphericalGeographyUtils.CartesianPoint cp = new SphericalGeographyUtils.CartesianPoint(p);
        Point newPoint = SphericalGeographyUtils.cartesianPointToPoint(cp);
        assert Math.abs(newPoint.getY() - p.getY()) <= 0.000001;
        assert Math.abs(newPoint.getX() - p.getX()) <= 0.000001;
    }

//    @Test
//    public void testConversion()
//    {
//        Point origin = new Point(-90, 40);
//        Point p1 = new Point(-180, 90);
//        Point p2 = new Point(180, 90);
//        Point cp = SphericalGeographyUtils.nearestPointOnGreatCircle(origin, p1, p2);
//        System.out.println("X: " + cp.getX() + " Y: " + cp.getY() + " Z: " + cp.getZ());
//        double dist = SphericalGeographyUtils.distanceBetweenPointToArc(origin, p1, p2);
//        System.out.println(dist);
//        assert 1 == 0;
//    }

//    @Test
//    public void testParsing()
//    {
//        double longitude = -90;
//        double latitude = 40;
//        String wkt = "MULTIPOLYGON(((-180 -90, -180 90, 180 90, 180 -90, -180 -90)))";
//        Point origin = new Point(longitude, latitude);
//        OGCGeometry geometry = EsriGeometrySerde.deserialize(wkt);
//        if (!Objects.equals(geometry.geometryType(), OGCMultiPolygon.TYPE) && !Objects.equals(geometry.geometryType(), OGCPolygon.TYPE)) {
//            throw new PrestoException(INVALID_FUNCTION_ARGUMENT, format("%s is not supported, please provide Polygon or MultiPolygon instead.", geometry.geometryType()));
//        }
//        Polygon polygon = (Polygon) geometry.getEsriGeometry();
//        int n = polygon.getPathCount();
//        double minDistance = Double.MAX_VALUE;
//        for (int i = 0; i < n - 1; i++) {
//            Point p1 = polygon.getPoint(i);
//            Point p2 = polygon.getPoint(i + 1);
//
//            double dist = SphericalGeographyUtils.distanceBetweenPointToArc(origin, p1, p2);
//            if (dist < minDistance) {
//                minDistance = dist;
//            }
//        }
//    }

//    @Test
//    public void testNearestPointOnGreatCircle()
//    {
//        Point origin = new Point(10, 13);
//        Point arcStart = new Point(-21, 19);
//        Point arcEnd = new Point(32, 38);
//        Point result = SphericalGeographyUtils.nearestPointOnGreatCircle(origin, arcStart, arcEnd);
//        System.out.println(result);
//
//        SphericalGeographyUtils.CartesianPoint cartesianPointOrigin = new SphericalGeographyUtils.CartesianPoint(origin);
//        SphericalGeographyUtils.CartesianPoint cartesianPointArcStart = new SphericalGeographyUtils.CartesianPoint(arcStart);
//        SphericalGeographyUtils.CartesianPoint cartesianPointArcEnd = new SphericalGeographyUtils.CartesianPoint(arcEnd);
//        SphericalGeographyUtils.CartesianPoint arcPlane = cartesianPointArcStart.crossProduct(cartesianPointArcEnd);
//        SphericalGeographyUtils.CartesianPoint intersectingPlane = cartesianPointOrigin.crossProduct(arcPlane);
//        SphericalGeographyUtils.CartesianPoint plane = arcPlane.crossProduct(intersectingPlane);
//        System.out.println("Printing arc plane");
//        System.out.println(arcPlane.getX());
//        System.out.println(arcPlane.getY());
//        System.out.println(arcPlane.getZ());
//        System.out.println("Printing intersecting plane");
//        System.out.println(intersectingPlane.getX());
//        System.out.println(intersectingPlane.getY());
//        System.out.println(intersectingPlane.getZ());
//        System.out.println("Printing plane");
//        System.out.println(plane.getX());
//        System.out.println(plane.getY());
//        System.out.println(plane.getZ());
////        Point nearestPoint = SphericalGeographyUtils.cartesianPointToPoint(plane);
//        double radius = Math.sqrt(Math.pow(plane.getX(), 2) + Math.pow(plane.getY(), 2) + Math.pow(plane.getZ(), 2));
//        double longitude = Math.toDegrees(Math.atan2(plane.getY(), plane.getX()));
//        double latitude = Math.toDegrees(Math.asin(plane.getZ() / radius));
//        Point nearestPoint = new Point(longitude, latitude);
//
//        System.out.println("CLOSEST POINT");
//        System.out.println(nearestPoint.getY());
//        System.out.println(nearestPoint.getX());
//
//        double x = 4.9206758551494185E18;
//        double y = 1.6658339591582016E17;
//        double z = 2.9405722611770353E18;
//        radius = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
//        System.out.println(Math.asin(z / radius));
//        System.out.println(Math.toDegrees(Math.asin(z / radius)));
//
//        double dist = SphericalGeographyUtils.greatCircleDistance(arcStart.getY(), arcStart.getX(), arcEnd.getY(), arcEnd.getX());
//        System.out.println(dist);
//
//        // distanceBetweenPointToArc
//        double arcLength = SphericalGeographyUtils.greatCircleDistance(arcStart.getY(), arcStart.getX(), arcEnd.getY(), arcEnd.getX());
//        double distPointToStart = SphericalGeographyUtils.greatCircleDistance(arcStart.getY(), arcStart.getX(), nearestPoint.getY(), nearestPoint.getX());
//        double distPointToEnd = SphericalGeographyUtils.greatCircleDistance(arcEnd.getY(), arcEnd.getX(), nearestPoint.getY(), nearestPoint.getX());
//        final double precision = 0.001; // in km
//        double distanceBetweenPointToArc = 0;
//        if (Math.abs(arcLength - distPointToStart - distPointToEnd) < precision) {
//            System.out.println("HEREEE");
//            distanceBetweenPointToArc = SphericalGeographyUtils.greatCircleDistance(nearestPoint.getY(), nearestPoint.getX(), origin.getY(), origin.getX());
////            System.out.println(SphericalGeographyUtils.haversineDistance(nearestPoint.getY(), nearestPoint.getX(), origin.getY(), origin.getX()));
//        }
//        else {
//            double distOriginToStart = SphericalGeographyUtils.greatCircleDistance(origin.getY(), origin.getX(), arcStart.getY(), arcStart.getX());
//            double distOriginToEnd = SphericalGeographyUtils.greatCircleDistance(origin.getY(), origin.getX(), arcEnd.getY(), arcEnd.getX());
//
//            distanceBetweenPointToArc = SphericalGeographyUtils.distanceBetweenPointToArc(origin, arcStart, arcEnd);
//        }
//        System.out.println(distanceBetweenPointToArc);
//
//        assert Math.abs(result.getY() - 30.847889192843226) <= 0.00001;
//        assert Math.abs(result.getX() - 1.9389372627693584) <= 0.00001;
//    }

    @Test
    public void testNearestPointOnGreatCircle()
    {
        Point origin = new Point(10, 13);
        Point arcStart = new Point(-21, 19);
        Point arcEnd = new Point(32, 38);
        Point result = SphericalGeographyUtils.nearestPointOnGreatCircle(origin, arcStart, arcEnd);

        assert Math.abs(result.getY() - 30.847889192843226) <= 0.00001;
        assert Math.abs(result.getX() - 1.9389372627693584) <= 0.00001;

        Point result1 = SphericalGeographyUtils.nearestPointOnGreatCircle(origin, arcStart, arcEnd);
        Point result2 = SphericalGeographyUtils.nearestPointOnGreatCircle(origin, arcEnd, arcStart);
        assert result1.getX() == result2.getX();
        assert result1.getY() == result2.getY();
    }

    @Test
    public void testDistanceBetweenPointToArc()
    {
        Point origin = new Point(10, 13);
        Point arcStart = new Point(-21, 19);
        Point arcEnd = new Point(32, 38);
        double result = SphericalGeographyUtils.distanceBetweenPointToArc(origin, arcStart, arcEnd);
        System.out.println(result);
        assert Math.abs(result - 2149.7787568177105) <= 0.01;
    }
}
