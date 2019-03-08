# 3D-Neuronal-Segmentation
Faster 3D segmentation algorithm ideal for high-resolution image stacks of cortical neurones. Using slice-by-slice canny edge detection (openCV) followed by two-pass component labelling. Parallel Soma detection using openCV's Simple Blob Detection. Manual inputs required for min soma area, threshold, and circularity. Example _run script included. Only run this script. 


A neuron tracing algorithm strives to take a 3D grayscale image and output a forest of clearly-defined neurons, where each neuron is a rooted tree graph model. To obtain this forest, Neurotracer starts with canny edge detection. This generates a binary map, which is dilated to fill in the edges. The resulting map is skeletonized. In parallel, all the somas are found in the image via a blob detector. The somas are filled and the image undergoes two-pass connected component labelling, with the soma points as the seed points. Statistics, such as endpoints, bifurcation points, and approximate axonal length can be found from the skeleton. Finally, the skeleton is usually dilated for easy viewing.
Our ImageJ Plug-in makes extensive use of the OpenCV library and Skeletonize3D (ImageJ Plugin). One downside of the OpenCV library is that it has little 3D functionality. However, we were able to achieve sufficient results and effectively speed up the algorithm by applying the library functions on 2D 	slices.


Fig. 1 - Neurotracer Programming Flowchart
A. Binary Segmentation
	To obtain a binary segmentation map, Neurotracer applies the OpenCV canny edge detection function slice-by-slice. We chose Canny edge detection to initially filter the image, because it gave the best output when compared to other methods, such as LoG (Laplacian of Gaussian), non-local means denoising, and median filtering. To create a filled-in segment, Neurotracer dilates the binary image. Other filling methods are not feasible because of irregular morphology. Finally, Neurotracer performs a medial axis transform by iterative thinning through calculations of the object boundary distance transforms on the filled in binary map, via the skeletonize3D Plugin, to create a skeleton to trace and to locate the endpoints and bifurcation points. 
B. Soma Detection
	Most Soma detection algorithms recursively apply a LoG of varying sizes and find the soma points and their size by applying NMS (non-maximum suppression). While these algorithms can easily be executed in 3D, they are poor at detecting non-spherical somas and hold a high computational cost. Instead, Neurotracer processes the image slice-by-slice, applying a median filter of approximately the same size as the soma and then using the Simple Blob Detector in the OpenCV library. While a median filter does add computational time, when compared to other methods that denoise (e.g. mean kernel), it is able to preserve edges because of its non-linearity. The Simple Blob Detector is easily configurable, able to filter objects by color, area, circularity, convexity, and inertia ratios. It works by...Neurotracer then applies a simple distance clustering algorithm to cluster all the soma points belonging to one soma and averages the points to find the soma center point. Clustering prevents multiple recognition of the same soma and assuming a gaussian distribution of soma points, averaging gives the best approximation for the center.
C. Tracing
	Neurotracer traces the neurons in the image by applying a two-pass connected-component labelling algorithm. Then it selectively highlights the neuron, by coloring every voxel with the same value as the local neighborhood of the soma center, ignoring background pixels. This effectively removes any non-local signal not directly connected to the soma (i.e. branch). Two pre-processing methods are used prior to two-component labelling:
	Mosaic Visualization (see Fig. 2): Original skeletonized image stack undergoes binary      dilatation for more robust and easy-to-see traces
	 Post-Processing and BRISK comparison: Image skeleton is left untouched and endpoints are found for quantitative characterization 
D. Trace Analysis
Neurotracer searches for the endpoints and bifurcation points by finding all voxels with less than two or more than two neighbors respectively. The axonal length is calculated by finding the distance between the soma center and the furthest end point. Although the axonal length is not calculated exactly, it’s approximation is accurate enough to be used for comparison with other neurons. Finally, the image is dilated again to increase visibility.
E. Trace Validation
	We used semi-autonomous Simple Neurite Tracer (ImageJ Plugin) traces as our groundtruth. We compared Neurotracer against the Neuronal Image Analyzer by measuring their feature similarity with the ground truth using twenty slice overlays via the BRISK OpenCV feature detector. We used Neuronal Image Analyzer as the alternative tracing method, since it is able to automatically trace multiple neurons in 3D volumes. We decided to compare 2D overlays, instead of the 3D volume to save computational time. 

For our trace validation, we found that our Neurotracer results were clearly superior than that of Neuron Image Analyzer (NIA) [1] (Fig. 3) which provided pretty basic mapping. Furthermore, performing the BRISK OpenCV feature detector yielded the following quantitative results:

NIA
124 matches out of 2498 total feature points.
0 good matches.

Neurotracer
4835 matches out of 7206 total feature points.
27 good matches.



Java wrapper for OpenCV can be found via this link: https://github.com/joheras/IJ-OpenCV

Download ImageJ Skeletonization3D API here: https://github.com/fiji/Skeletonize3D/releases/tag/Skeletonize3D_-2.1.1

Download ImageJ/Fiji here: https://imagej.net/Fiji/Downloads
