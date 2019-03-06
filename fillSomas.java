package neural_tracer_classes;

import org.bytedeco.javacpp.opencv_core;
import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageConverter;
import org.bytedeco.javacpp.opencv_core.KeyPointVectorVector;

public class fillSomas {
	
	public ImagePlus image;
	public ImagePlus outPut;
	public KeyPointVectorVector soma_points;
	
	public fillSomas(ImagePlus inpImage, KeyPointVectorVector soma_pointsInp) {
		image = inpImage;
		soma_points = soma_pointsInp;
	}	
	
	public void run(){
        ImageStack stack=image.getStack();
        for (int i=0;i<stack.getSize();i++)
        {
              opencv_core.KeyPointVector soma_pts_slice=soma_points.get(i);
              for (int j=0;j<soma_pts_slice.size();j++)
              {
                     opencv_core.KeyPoint temp_point=soma_pts_slice.get(j);
                     int xpos=(int) temp_point.pt().x();
                     int ypos=(int) temp_point.pt().y();
                     int rad=(int) temp_point.size();                    
                     //draw circles                   
                     for (int k=-rad/2;k<=rad/2;k++)
                     {
                           for (int l=-rad/2;l<=rad/2;l++)
                           {
                                  if (Math.sqrt(Math.pow(l, 2)+Math.pow(k, 2))<=rad/2)
                                  {
                                        stack.setVoxel(xpos+k, ypos+l, i, 255);
                                  }
                           }
                    }
              }            
        }
       
        ImagePlus out=new ImagePlus ("filled in Somas",stack);
       
        ImageConverter cv = new ImageConverter(out);
        cv.convertToGray8();
        out.updateImage();    
        outPut = out; 
	}
	public ImagePlus outputImage() {
		return outPut;
	}
}
