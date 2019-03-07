package neural_tracer_classes;

//for ImageJ functionality
import net.imagej.ImageJ;


//ImageJ object classes


import ij.ImagePlus;
import ij.process.ImageProcessor;


//StarLite Algorithm

//ImageJ-OpenCV conversion objects
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.MatImagePlusConverter;
//OpenCV Java objects
import org.bytedeco.javacpp.opencv_core;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_features2d;
import org.bytedeco.javacpp.opencv_features2d.BRISK;

//Java objects
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
//more java objects

//tracer files

/**
*
* @author Mark
*/
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>IJ-OpenCV-plugins>Evaluate")
public class Evaluation implements Command {
@Parameter
  private ImagePlus imp;
    
  @Override
  public void run() {
	  
	  //first image is test image
	  //second image must be ground truth
	  //Note that image sizes must be the same for the BRISK feature matcher to be accurate
	  
	  ImagePlusMatConverter icc = new ImagePlusMatConverter();
	  MatImagePlusConverter mipp = new MatImagePlusConverter(); 
	  
	  ImageProcessor proc1=imp.getStack().getProcessor(1);
	  ImageProcessor proc2=imp.getStack().getProcessor(2);
	  
	  ImagePlus temp1=new ImagePlus("",proc1);
	  ImagePlus temp2=new ImagePlus("",proc2);
	  
	  Mat m1=icc.convert(temp1, Mat.class);
	  Mat m2=icc.convert(temp2, Mat.class);
	  
	  //opencv_imgproc.pyrUp(m1, m1, m2.size(), opencv_core.BORDER_DEFAULT);		

	  
	  opencv_core.KeyPointVector feature_points1=new opencv_core.KeyPointVector();
	  opencv_core.KeyPointVector feature_points2=new opencv_core.KeyPointVector();
	  
	  BRISK brisk = opencv_features2d.BRISK.create(40, 3, 1.0f);
	  
	  
	  opencv_core.Mat descriptors1=new opencv_core.Mat();
	  opencv_core.Mat mask1=new opencv_core.Mat();
	  brisk.detectAndCompute(m1, mask1, feature_points1, descriptors1);
	  
	  opencv_core.Mat descriptors2=new opencv_core.Mat();
	  opencv_core.Mat mask2=new opencv_core.Mat();
	  brisk.detectAndCompute(m2, mask2, feature_points2, descriptors2);
	  opencv_features2d.drawKeypoints(m1, feature_points1, m1);
	  opencv_features2d.drawKeypoints(m2, feature_points2, m2);
	  
	  opencv_features2d.BFMatcher bf = new opencv_features2d.BFMatcher();
	  
	  opencv_core.DMatchVectorVector matches=new opencv_core.DMatchVectorVector();
	  bf.knnMatch(descriptors1, descriptors2, matches, 2);
	  opencv_core.Mat output=new opencv_core.Mat();
	  opencv_core.DMatchVector match=new opencv_core.DMatchVector();
	 
	  for(int i=0;i<matches.size();i++)
	  {
		  if(matches.get(i).get(0).distance()<0.75*matches.get(i).get(1).distance())
		  {
			  match.push_back(matches.get(i).get(0));
		  }
	  }
	 
	  System.out.println("total number of feature points:");
	  System.out.println(feature_points1.size()+feature_points2.size());
	  
	  System.out.println("number of 'good' feature points:");
	  System.out.println(match.size());
	  
	  System.out.println("total number of matches:");
	  System.out.println(matches.size());
	
	  
	  
	  opencv_features2d.drawMatches(m1, feature_points1, m2, feature_points2, match, output);
	  
	  ImagePlus out1=mipp.convert(m1, ImagePlus.class);
	  ImagePlus out2=mipp.convert(m2, ImagePlus.class);
	  ImagePlus out3=mipp.convert(output,ImagePlus.class);
	  
	  out1.show();
	  out2.show();
	  out3.show();
	  bf.close();
	
  }
  
  public static void main(final String[] args) throws Exception {
      final ImageJ ij = new ImageJ();
      ij.launch(args);
   }


}
  
