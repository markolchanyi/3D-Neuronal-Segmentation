package neural_tracer_classes;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_features2d;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_core.KeyPointVector;
import org.bytedeco.javacpp.opencv_core.KeyPointVectorVector;
import org.bytedeco.javacpp.opencv_core.Mat;

import ij.ImagePlus;
import ij.ImageStack;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.MatImagePlusConverter;

public class findSomas {
	public findSomas(ImagePlus imageInp) {
		image = imageInp;
	}
    ImagePlusMatConverter ic = new ImagePlusMatConverter();
    MatImagePlusConverter mip = new MatImagePlusConverter();
    opencv_core.KeyPointVectorVector soma_points=new opencv_core.KeyPointVectorVector();
    opencv_features2d.Feature2D f2d = new opencv_features2d.Feature2D();
    opencv_features2d.SimpleBlobDetector.Params parameters=new opencv_features2d.SimpleBlobDetector.Params();
    
	public ImagePlus image;
	
	
	 public void run(){
         parameters.minThreshold(170);
         parameters.maxThreshold(255);
         parameters.filterByArea(true);
         parameters.minArea(65);
         parameters.maxArea(500);
         parameters.filterByCircularity(true);
         parameters.minCircularity((float)0.2);
         parameters.filterByConvexity(true);
         parameters.minConvexity((float)0.87);
         parameters.filterByInertia(true);
         parameters.minInertiaRatio((float)0.1);     
         f2d=opencv_features2d.SimpleBlobDetector.create(parameters);  
         ImageStack stack=image.getStack();
         KeyPointVector kpv = new opencv_core.KeyPointVector();
         for (int i=1;i<=stack.getSize();i++) {     
	         ImagePlus temp_image=new ImagePlus("temp",stack.getProcessor(i));
	         opencv_core.Mat m=ic.convert(temp_image, Mat.class);
	         opencv_imgproc.medianBlur(m, m, 21);
	         opencv_core.bitwise_not(m, m);
	         f2d.detect(m, kpv);
	         soma_points.push_back(kpv);
	         kpv.clear();
         }    
	 }
	 
	 public KeyPointVectorVector getKPVV() {
		 return soma_points;
	 }

}
