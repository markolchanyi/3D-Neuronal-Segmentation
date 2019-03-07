package neural_tracer_classes;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.MatImagePlusConverter;

//use openCV to determine edges followed by filling with dilation and median filtering
public class canny2d {
	
	public ImagePlus image;
	public ImagePlus outputImage;
 	ImagePlusMatConverter icc = new ImagePlusMatConverter();
 	MatImagePlusConverter mipp = new MatImagePlusConverter();
	//constructor requires only ImagePlus
 	public canny2d(ImagePlus imageInp) {
 		image = imageInp;
 	}
 	

 	public void run() {
 	
	 	ImageStack stack=image.getStack();
	 	ImageStack stackxy_out=new ImageStack(stack.getWidth(),stack.getHeight());
	 	
	 	//iterate through slices
	 	for (int i=1;i<=stack.getSize();i++)
	 	{
	 		ImageProcessor slice=stack.getProcessor(i);
	 		ImagePlus temp_image=new ImagePlus("temp",slice);
	 		Mat m=icc.convert(temp_image, Mat.class);
	 		
	 		opencv_imgproc.Canny(m, m, 55, 210);
	 		opencv_imgproc.threshold(m, m, 0, 255, opencv_imgproc.THRESH_OTSU); 
	 		
	 	      
	 	      //dilate image to fill in edges
	 		final Size ksize = new Size(3, 3);       
	 	    Mat kernel = opencv_imgproc.getStructuringElement(Imgproc.MORPH_CROSS, ksize);
	 	    opencv_imgproc.dilate(m, m, kernel, new opencv_core.Point(-1,-1), 1, opencv_core.BORDER_CONSTANT, opencv_core.Scalar.BLACK); 
	 	    opencv_imgproc.medianBlur(m, m, 3);
	 	    opencv_imgproc.distanceTransform(m, m, opencv_imgproc.DIST_L1, 5);    	 	      
	 	    //now threshold again and turn into binary image (pixel values 255 or 0)
	 	    opencv_imgproc.threshold(m, m, 0.7 * 1, 255, opencv_imgproc.CV_THRESH_BINARY);
	 		
	 		temp_image=mipp.convert(m, ImagePlus.class);
	 		stackxy_out.addSlice(temp_image.getProcessor());
	 		
	 	}
	 	ImagePlus imagexy=new ImagePlus("Canny in xy dims",stackxy_out);	 	
	 	ImageConverter cv = new ImageConverter(imagexy);
	 	cv.convertToGray8();
	 	imagexy.updateImage();
	 	outputImage = imagexy;
 	}
 	
    public ImagePlus returnImage() {
 		return outputImage;
 	}

}
