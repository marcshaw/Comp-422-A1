package q1_2;

import ij.ImagePlus;
import ij.gui.ImageWindow;
import ij.process.ImageProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Q1_2b {


	int matrixSize = 5;

	public Q1_2b(String dir){

		ImagePlus image = new ImagePlus(dir  + "/1.2/ckt-board-saltpep.tif");
		ImageProcessor ip = image.getProcessor();

		int w = ip.getWidth();
		int h = ip.getHeight();

		List<Float> pixelList = new ArrayList<Float>();

		ImageProcessor copy = ip.duplicate();

		int startX = matrixSize/2;


		for (int x=startX; x < w; x++) {
			for (int y=startX; y < h; y++) {



				for(int filterX = -startX; filterX < startX + 1; filterX++) {
					for(int filterY = -startX; filterY < startX + 1; filterY++) {

						if(!(x + filterX < 0 || x +filterX > w || y + filterY < 0 || y + filterY > h)){
							pixelList.add(copy.getPixelValue(x + filterX, y + filterY));
						}

					}
				}

				Collections.sort(pixelList);

				Float pixel_x;

				//Depends on number of points, will depend on the 'middle' value
				if(pixelList.size() % 2 == 0){
					Float one = pixelList.get(((pixelList.size()/2) -1));
					Float two = pixelList.get((pixelList.size()/2));
					pixel_x = (one + two)/2;
				}
				else{
					if(pixelList.size() == 1){
						pixel_x = pixelList.get(0);
					}
					else{
						pixel_x = pixelList.get((int) ((pixelList.size()/2) + 0.5));

					}}

				//Bound the value
				if(pixel_x < 0)
				{
					pixel_x = (float) 0.0;
				}

				if(pixel_x > 255)
				{
					pixel_x = (float) 255.0;
				}

				ip.putPixelValue(x,y,pixel_x);


				pixelList.clear();
			}
		}

		ImageWindow window = new ImageWindow(image);

	}



}
