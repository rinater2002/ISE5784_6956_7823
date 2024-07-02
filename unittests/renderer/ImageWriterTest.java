package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * ImageWriterTest is a test class for the ImageWriter class.
 * It creates an image with a grid and verifies the image creation.
 */
class ImageWriterTest {

    /**
     * Test method for {@link renderer.ImageWriter#writeToImage()}.
     * This test creates an image with a yellow background and red grid lines.
     */
    @Test
    void testWriteToImage() {
        // Create an ImageWriter object with the specified name and resolution
        ImageWriter imageWriter = new ImageWriter("yellow", 800, 500);

        // Loop through each pixel in the image
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                // Draw vertical grid lines
                if (i % 50 == 0) {
                    imageWriter.writePixel(i, j, new Color(java.awt.Color.RED));
                }
                // Draw horizontal grid lines
                else if (j % 50 == 0) {
                    imageWriter.writePixel(i, j, new Color(java.awt.Color.RED));
                } else {
                    // Fill the rest of the pixels with yellow
                    imageWriter.writePixel(i, j, new Color(java.awt.Color.YELLOW));
                }
            }
        }

        // Write the image to a file
        imageWriter.writeToImage();
    }
}
