/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.Plane;
import geometries.Polygon;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        scene.geometries.add(
                new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setkL(0.0004).setkQ(0.0000006));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(150, 150)
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }


    /**
     * /** Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        scene.geometries.add(
                new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)
                                .setkT(new Double3(0.5, 0, 0))),
                new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setkR(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setkR(new Double3(0.5, 0, 0.4))));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
                .setkL(0.00001).setkQ(0.000005));

        cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
                .setVpSize(2500, 2500)
                .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                        .setkL(4E-5).setkQ(2E-7));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }


    /**
     * Produce a picture of five objects lighted by a spot light and point light
     * to show all the effects in one picture
     */
    @Test
    public void reflectionRefractionFiveObjectsTest() {

        this.scene.setAmbientLight(new AmbientLight(new Color(YELLOW), new Double3(0.15)));

        this.scene.geometries.add( //
                new Triangle(new Point(-150, -150, -115),
                        new Point(150, -150, -135),
                        new Point(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5)
                                .setnShininess(60)), //

                new Triangle(new Point(-150, -150, -115),
                        new Point(-70, 70, -140),
                        new Point(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5)
                                .setnShininess(60)), //

                new Sphere(new Point(60, 50, -50), 30) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2)
                                .setnShininess(30).setkT(0.6)),

                new Sphere(new Point(-50, -100, 100), 25.7) //
                        .setEmission(new Color(green)) //
                        .setMaterial(new Material().setkD(0.002).setkS(0.2)
                                .setnShininess(30).setkT(0.9)),

                new Sphere(new Point(-50, -80, 100), 17) //
                        .setEmission(new Color(green)) //
                        .setMaterial(new Material().setkD(0.002).setkS(0.2)
                                .setnShininess(30).setkT(0.9)),

                new Sphere(new Point(30, -60, 100), 22) //
                        .setEmission(new Color(cyan)) //
                        .setMaterial(new Material().setkD(0.002).setkS(0.2)
                                .setnShininess(30).setkT(0.9)),

                new Sphere(new Point(-60, 50, 100), 10) //
                        .setEmission(new Color(yellow)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2)
                                .setnShininess(2).setkT(0.8)));

        this.scene.lights.add(new SpotLight(new Color(700, 400, 400),
                new Point(30, 25, 0),
                new Vector(0, 0, -1)) //
                .setkL(4E-5).setkQ(2E-7));

        this.scene.lights.add(new PointLight(new Color(160, 80, 240),
                new Point(-100, -100, 100))//
                .setkL(0.00000000001).setkQ(0.0000000001));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("reflectionRefractionFiveObjectsTest", 600, 600))
                .build()
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void testBlurryGlass() {

        Vector vTo = new Vector(0, 1, 0);
        Camera.Builder camera =Camera.getBuilder().setLocation(new Point(0,-230,0).add(vTo.scale(-13)))
                .setDirection(vTo,new Vector(0,0,1))
                .setVpSize(200d, 200).setVpDistance(1000);
        ;

        scene.setAmbientLight(new AmbientLight(new Color(gray).reduce(2), new Double3(0.15)));

        for (int i = -4; i < 6; i += 2) {
            scene.geometries.add(
                    new Sphere(new Point(5 * i, -1.50, -3),3).setEmission(new Color(WHITE).reduce(4).reduce(2))
                            .setMaterial(new Material().setkD(0.2).setkS(1).setnShininess(80).setkT(0d)),

                    new Sphere( new Point(5 * i, 5, 3),3).setEmission(new Color(BLUE).reduce(2))
                            .setMaterial(new Material().setkD(0.2).setkS(1).setnShininess(80).setkT(0d)),
                    new Sphere(new Point(5 * i, -8, -8),3).setEmission(new Color(PINK).reduce(2))
                            .setMaterial(new Material().setkD(0.2).setkS(1).setnShininess(80).setkT(0d)),

                    new Polygon(new Point(5 * i - 4, -5, -11), new Point(5 * i - 4, -5, 5), new Point(5 * i + 4, -5, 5),
                            new Point(5 * i + 4, -5, -11)).setEmission(new Color(250, 235, 215).reduce(2))
                            .setMaterial(new Material().setkD(0.001).setkS(0.002).setnShininess(1).setkT(0.95)
                                    .setBlurGlass(i == 4 ? 1 : 1000, 0.9 * (i + 15), 17))

            );
        }

        scene.geometries.add(new Plane(new Point(1, 10, 1), new Point(2, 10, 1), new Point(5, 10, 0))
                .setEmission(new Color(white).reduce(3))
                .setMaterial(new Material().setkD(0.2).setkS(0).setnShininess(0).setkT(0d))

        );

        // scene.lights.add(new PointLight(new Color(100, 100, 150), new Point(0, 6,
        // 0)));
        scene.lights.add(new DirectionalLight(new Color(white).reduce(1), new Vector(-0.4, 1, 0)));
        scene.lights.add(new SpotLight(new Color(white).reduce(2), new Point(20.43303, -7.37104, 13.77329),
                new Vector(-20.43, 7.37, -13.77)).setkL(0.6));

        ImageWriter imageWriter = new ImageWriter("blurryGlass2", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new SimpleRayTracer(scene)) //
                .build() //
                .renderImage()
                .writeToImage();

    }





}
/**
 @Test
 public void drawHouseWithSunReflectionAndShadow() {
 Scene scene = new Scene("House with Sun Reflection and Shadow");

 // House body
 scene.geometries.add(
 new Polygon(
 new Point(-50, -50, 0),
 new Point(50, -50, 0),
 new Point(50, 0, 0),
 new Point(-50, 0, 0)
 ).setEmission(new Color(150, 75, 0)) // Brown color
 .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30).setkR(0.5))
 );

 // Roof
 scene.geometries.add(
 new Triangle(
 new Point(-50, 0, 0),
 new Point(50, 0, 0),
 new Point(0, 50, 0)
 ).setEmission(new Color(255, 0, 0)) // Red color
 .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30).setkR(0.5))
 );

 // Door
 scene.geometries.add(
 new Polygon(
 new Point(-10, -50, 0),
 new Point(10, -50, 0),
 new Point(10, 0, 0),
 new Point(-10, 0, 0)
 ).setEmission(new Color(100, 50, 0)) // Dark brown color
 .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30).setkR(0.5))
 );

 // Windows
 /**
 scene.geometries.add(
 new Polygon(
 new Point(20, -20, 0),
 new Point(40, -20, 0),
 new Point(40, 20, 0),
 new Point(20, 20, 0)
 ).setEmission(new Color(30, 144, 255)) // Light blue color
 .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30).setkR(0.5))
 );

 // Sun
 scene.geometries.add(
 new Sphere(new Point(40, 20, -40), 30) //
 .setEmission(new Color(yellow)) //
 .setMaterial(new Material().setkD(0.7).setkS(0.1)
 .setnShininess(2).setkT(0.9))
 );

 // Ground plane for reflection
 scene.geometries.add(
 new Polygon(
 new Point(-100, -100, -50),
 new Point(100, -100, -50),
 new Point(100, 100, -50),
 new Point(-100, 100, -50)
 ).setEmission(new Color(20, 20, 20)) // Dark gray color
 .setMaterial(new Material().setkR(0.7).setkT(0.3))
 );

 // Add light source
 scene.lights.add(
 new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
 .setkL(4E-5).setkQ(2E-7));

 // Configure the camera and render the image
 Camera camera = Camera.getBuilder()
 .setLocation(new Point(0, 0, 100))
 .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
 .setVpDistance(100)
 .setVpSize(200, 200)
 .setImageWriter(new ImageWriter("houseWithSunReflectionAndShadow", 500, 500))
 .setRayTracer(new SimpleRayTracer(scene))
 .build();

 camera.renderImage();
 camera.writeToImage();
 }

 */
