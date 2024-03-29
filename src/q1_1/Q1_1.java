package q1_1;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import q1_2.Q1_2b;
import q2_1.Q2_1;
import ij.ImagePlus;
import ij.gui.ImageWindow;
import ij.process.*;


public class Q1_1 {


    private int mask_X[][] = {{-1,0,1},
                       {-2,0,2},
                       {-1,0,1}};

    private int mask_Y[][] = {{-1,-2,-1},
                       {0,0,0},
                       {1,2,1}};


	public Q1_1(String dir){

		ImagePlus image = new ImagePlus(dir  + "/1.1/test-pattern.tif");

		ImageProcessor ip = image.getProcessor();

		run(ip);

		final ImageWindow window = new ImageWindow(image);

		window.pack();

		window.addWindowStateListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				window.dispose();
			}
		}
		);
	}


    public void run(ImageProcessor ip) {


        int w = ip.getWidth();
        int h = ip.getHeight();
        ImageProcessor copy = ip.duplicate();

        //Getting the offset value
		int startX = mask_X.length/2;


        for (int x=1; x < w-2; x++) {
            for (int y=1; y < h-2; y++) {

                int pixel_x = 0;
                int pixel_y = 0;

                //Looping around the center node
                //Dependent on the offset value
				for(int filterX = -startX; filterX < startX + 1; filterX++) {
					for(int filterY = -startX; filterY < startX + 1; filterY++) {

						pixel_x += copy.getPixelValue(x + filterX, y + filterY) * mask_X[filterX+startX][filterY+startX];
					}
				}

				for(int filterX = -startX; filterX < startX + 1; filterX++) {
					for(int filterY = -startX; filterY < startX + 1; filterY++) {

						pixel_y += copy.getPixelValue(x + filterX, y + filterY) * mask_Y[filterX+startX][filterY+startX];
					}
				}

				//Calculating the new value
                int val = (int)Math.sqrt((pixel_x * pixel_x) + (pixel_y * pixel_y));

                if(val < 0)
                {
                   val = 0;
                }

                if(val > 255)
                {
                   val = 255;
                }

                ip.putPixel(x,y,val);
            }
        }
    }
}