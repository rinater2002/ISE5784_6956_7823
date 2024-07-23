package lighting;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering a basic image
 * @author Dan
 */
public class LightsTests {
    /** First scene for some of tests */
    private final Scene          scene1                  = new Scene("Test scene");
    /** Second scene for some of tests */
    private final Scene          scene2                  = new Scene("Test scene")
            .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

    /** First camera builder for some of tests */
    private final Camera.Builder camera1                 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene1))
            .setLocation(new Point(0, 0, 1000))
            .setDirection(new Vector(0,0,-1), new Vector(0,1,0))
            .setVpSize(150, 150).setVpDistance(1000);
    /** Second camera builder for some of tests */
    private final Camera.Builder camera2                 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene2))
            .setLocation(new Point(0, 0, 1000))
            .setDirection(new Vector(0,0,-1), new Vector(0,1,0))
            .setVpSize(200, 200).setVpDistance(1000);

    /** Shininess value for most of the geometries in the tests */
    private static final int     SHININESS               = 301;
    /** Diffusion attenuation factor for some of the geometries in the tests */
    private static final double  KD                      = 0.5;
    /** Diffusion attenuation factor for some of the geometries in the tests */
    private static final Double3 KD3                     = new Double3(0.2, 0.6, 0.4);

    /** Specular attenuation factor for some of the geometries in the tests */
    private static final double  KS                      = 0.5;
    /** Specular attenuation factor for some of the geometries in the tests */
    private static final Double3 KS3                     = new Double3(0.2, 0.4, 0.3);

    /** Material for some of the geometries in the tests */
    private final Material       material                = new Material().setkD(KD3).setkS(KS3).setnShininess(SHININESS);
    /** Light color for tests with triangles */
    private final Color          trianglesLightColor     = new Color(800, 500, 250);
    /** Light color for tests with sphere */
    private final Color          sphereLightColor        = new Color(800, 500, 0);
    /** Color of the sphere */
    private final Color          sphereColor             = new Color(BLUE).reduce(2);

    /** Center of the sphere */
    private final Point          sphereCenter            = new Point(0, 0, -50);
    /** Radius of the sphere */
    private static final double  SPHERE_RADIUS           = 50d;

    /** The triangles' vertices for the tests with triangles */
    private final Point[]        vertices                =
            {
                    // the shared left-bottom:
                    new Point(-110, -110, -150),
                    // the shared right-top:
                    new Point(95, 100, -150),
                    // the right-bottom
                    new Point(110, -110, -150),
                    // the left-top
                    new Point(-75, 78, 100)
            };
    /** Position of the light in tests with sphere */
    private final Point          sphereLightPosition     = new Point(-50, -50, 25);
    /** Light direction (directional and spot) in tests with sphere */
    private final Vector         sphereLightDirection    = new Vector(1, 1, -0.5);
    /** Position of the light in tests with triangles */
    private final Point          trianglesLightPosition  = new Point(30, 10, -100);
    /** Light direction (directional and spot) in tests with triangles */
    private final Vector         trianglesLightDirection = new Vector(-2, -2, -2);

    /** The sphere in appropriate tests */
    private final Geometry       sphere                  = new Sphere(sphereCenter, SPHERE_RADIUS)
            .setEmission(sphereColor).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));
    /** The first triangle in appropriate tests */
    private final Geometry       triangle1               = new Triangle(vertices[0], vertices[1], vertices[2])
            .setMaterial(material);
    /** The first triangle in appropriate tests */
    private final Geometry       triangle2               = new Triangle(vertices[0], vertices[1], vertices[3])
            .setMaterial(material);

    /** Produce a picture of a sphere lighted by a directional light */
    @Test
    public void sphereDirectional() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new DirectionalLight(sphereLightColor, sphereLightDirection));

        camera1.setImageWriter(new ImageWriter("lightSphereDirectional", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /** Produce a picture of a sphere lighted by a point light */
    @Test
    public void spherePoint() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new PointLight(sphereLightColor, sphereLightPosition)
                .setkL(0.001).setkQ(0.0002));

        camera1.setImageWriter(new ImageWriter("lightSpherePoint", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /** Produce a picture of a sphere lighted by a spotlight */
    @Test
    public void sphereSpot() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(sphereLightColor, sphereLightPosition, sphereLightDirection)
                .setkL(0.001).setkQ(0.0001));

        camera1.setImageWriter(new ImageWriter("lightSphereSpot", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /** Produce a picture of two triangles lighted by a directional light */
    @Test
    public void trianglesDirectional() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new DirectionalLight(trianglesLightColor, trianglesLightDirection));

        camera2.setImageWriter(new ImageWriter("lightTrianglesDirectional", 500, 500)) //
                .build()
                .renderImage()
                .writeToImage();
    }

    /** Produce a picture of two triangles lighted by a point light */
    @Test
    public void trianglesPoint() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new PointLight(trianglesLightColor, trianglesLightPosition)
                .setkL(0.001).setkQ(0.0002));

        camera2.setImageWriter(new ImageWriter("lightTrianglesPoint", 500, 500)) //
                .build() //
                .renderImage() //
                .writeToImage(); //
    }

    /** Produce a picture of two triangles lighted by a spotlight */
    @Test
    public void trianglesSpot() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
                .setkL(0.001).setkQ(0.0001));

        camera2.setImageWriter(new ImageWriter("lightTrianglesSpot", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }


    /** Produce a picture of a sphere lighted by a narrow spotlight */
    @Test
    public void sphereSpotSharp() {
        scene1.geometries.add(sphere);
        scene1.lights
                .add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
                        .setkL(0.001).setkQ(0.00004).setNarrowBeam(10));

        camera1.setImageWriter(new ImageWriter("lightSphereSpotSharp", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /** Produce a picture of two triangles lighted by a narrow spotlight */
    @Test
    public void trianglesSpotSharp() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
                .setkL(0.001).setkQ(0.00004).setNarrowBeam(10));

        camera2.setImageWriter(new ImageWriter("lightTrianglesSpotSharp", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a one sphere lighted by a directional light, PointLight and spotlight.
     */
    @Test
    public void sphereIntegrationTest() {
        scene1.geometries.add(sphere);
        DirectionalLight directionalLight = new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1));
        PointLight pointLight = new PointLight(new Color(500, 300, 0), new Point(-300, 200, 100))//
                .setkL(0.00001).setkQ(0.000001);
        SpotLight spotLight = new SpotLight(new Color(500, 300, 0), new Point(250, 200, 100), new Vector(-1, -1, -1));

        scene1.lights.add(directionalLight);
        scene1.lights.add(pointLight);
        scene1.lights.add(spotLight);

        ImageWriter imageWriter = new ImageWriter("sphereIntegrationTest", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new SimpleRayTracer(scene1))
                .build()//
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a picture of two triangles lighted by a directional light, PointLight and spotlight.
     * and the sphere we have added
     */
    /**
     @Test
     public void multipleGeometriesAndLightings() {

     scene2.geometries.add(
     triangle1.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)),
     triangle2.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)),




     new Sphere(new Point(70,50,22),15).setEmission(new Color(BLUE).reduce(2))
     .setMaterial(new Material().setkD(0.97).setkS(1.0).setnShininess(300))
     );






     //== because we are in List that get only single value each time we separated to some section==//
     scene2.lights
     .add(new SpotLight(new Color(350, 350, 0), new Point(10, -10, -130), new Vector(-2, -2, -1))
     .setkL(0.0001)
     .setkQ(0.000005));

     scene2.lights
     .add(new PointLight(new Color(0, 300, 350), new Point(10, -10, -130))
     .setkL(0.0005).setkQ(0.0002));

     scene2.lights
     .add(new DirectionalLight(new Color(120, 5, 5), new Vector(0, 0, -1)));







     //=== another spotlight for our new sphere ===//
     scene2.lights
     .add(new SpotLight(new Color(400,250,800), new Point(2.5,6.3,7.5),new Vector(2.6,4.8,6.3)));

     ImageWriter imageWriter = new ImageWriter("multipleGeometriesAndLightings", 500, 500);
     camera2.setImageWriter(imageWriter) //
     .setRayTracer(new SimpleRayTracer(scene2)) //
     .build()
     .renderImage() //
     .writeToImage(); //
     }**/
    @Test
    public void multipleLightSourcesSphere() {
        Scene scene = new Scene("Test scene");
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.blue), new Double3(0.15,0,0)));

        scene.geometries.add(
                new Sphere(new Point(0, 0, -100),50)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100))
        );

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(-50, -50, 50), new Vector(1, 1, -2))
                .setkL(0.0001).setkQ(0.000005));
        scene.lights.add(new PointLight(new Color(500, 300, 0), new Point(50, 50, -50))
                .setkL(0.0005).setkQ(0.0005));
        scene.lights.add(new DirectionalLight(new Color(400, 300, 300), new Vector(1, 0, -1)));

        ImageWriter imageWriter = new ImageWriter("sphereMultipleLights", 500, 500);
        Camera camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, 0))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(150, 150)
                .setVpDistance(100)
                .setImageWriter(imageWriter)
                .setRayTracer(new SimpleRayTracer(scene))
                .build();

        camera.renderImage();
        camera.writeToImage();
    }


    @Test
    public void multipleLightSourcesTriangles() {
        Scene scene = new Scene("Test scene");
        scene.setAmbientLight(new AmbientLight(new Color(blue),new Double3(0.15,0,0)));

        scene.geometries.add(
                new Triangle(new Point(-150, 150, -150), new Point(150, 150, -150), new Point(75, -75, -150))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)),
                new Triangle(new Point(-150, 150, -150), new Point(-70, -70, -50), new Point(75, -75, -150))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300))
        );

        scene.lights.add(new SpotLight(new Color(133, 534, 200), new Point(60, 60, 100), new Vector(-1,-1,-2))
                .setkL(0.0001).setkQ(0.000005));
        scene.lights.add(new PointLight(new Color(890, 456, 675), new Point(-50, -50, -50))
                .setkL(0.0005).setkQ(0.0005));
        scene.lights.add(new DirectionalLight(new Color(121, 123, 324), new Vector(0, 0, -1)));

        ImageWriter imageWriter = new ImageWriter("trianglesMultipleLights", 500, 500);
        Camera camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, 100))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(200, 200)
                .setVpDistance(100)
                .setImageWriter(imageWriter)
                .setRayTracer(new SimpleRayTracer(scene))
                .build();

        camera.renderImage();
        camera.writeToImage();
    }
}


