package neural_tracer_classes;



import java.util.ArrayList;

import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageConverter;
import io.github.joheras.Point3d;



public class findBifPoints {
	public ImageStack imp;
	public int totalBif;
	public ArrayList<Point3d> somas;
	public ImagePlus blnk;
	public findBifPoints(ImageStack inp, ArrayList<Point3d> somasINP){
		imp = inp; 
		somas = somasINP;
	}
	
	public void run() {
		int height = imp.getHeight();
		int width = imp.getWidth();
		int size = imp.getSize();
		double voxelint;
		double centralVox;
		int counter;
		int counter2;
	    int iPos;
		int jPos;
		int kPos;
		int bifI;
		int bifJ;
		int bifK;
		double val;
		double intensity;
		int bifCounter;
		ArrayList<Point3d> bifPTS = new ArrayList<Point3d>();
		
		



		boolean [][][] ar = new boolean[height][width][size];
		
		for (int a = 0; a < size; a++) {
			for (int b = 0; b < height; b++) {
				for (int c = 0; c < width; c++) {
					ar[b][c][a] = false;
				}
			}
		}
		
		ArrayList<Point3d> bifPTScheck = new ArrayList<Point3d>();
		
		System.out.println("Bifrucation Points");
		
		for (int s = 0; s < somas.size(); s++) {
			iPos = somas.get(s).gety();
			jPos = somas.get(s).getx();
			kPos = somas.get(s).getz();
			intensity = imp.getVoxel(jPos, iPos, kPos);
			bifCounter = 0;	
			for (int k = 1; k < size-1; k++) {
				for(int i = 1; i < height-1; i++) {
					for (int j = 1; j < width-1; j++) {
						counter = 0;
						centralVox = imp.getVoxel(j, i, k);	
						if (centralVox == intensity) {
							for (int kk = k-1; kk <= k+1; kk++) {
								for (int ii = i-1; ii <= i+1; ii++) {
									for (int jj = j-1; jj <= j+1; jj++) {
										voxelint = imp.getVoxel(jj, ii, kk);
										if ((voxelint == centralVox)) {
							
											if (ar[ii][jj][kk] == true) {
												continue;
											}
											else {
												counter++;
											}
										}
									}
								}
							}
						}
						if (counter == 4) {
							ar[i][j][k] = true;
							bifCounter++;
							Point3d pt = new Point3d(j,i,k);
							bifPTScheck.add(pt);
							bifPTS.add(pt);
						}
						
					}
				}
			}
			
			System.out.println(bifCounter);
			totalBif = bifCounter;
		}
		

	}
	
	public int returnNum() {
		return totalBif;
	}

}
