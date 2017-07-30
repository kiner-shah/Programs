package KMeansImplementation;

import java.util.*;
import java.io.*;
class Point {
	int x, y, clusterNo;
}
class KMeans {
	public static void main(String args[]) throws IOException {
		Point points[] = new Point[150];
		int i;
		for(i = 0; i < 150; i++) {
			points[i] = new Point();
			points[i].x = (int) (Math.random() * 201);
			points[i].y = (int) (Math.random() * 201);
		}
		int index1, index2, index3;
		index1 = (int) (Math.random() * 150);
		index2 = (int) (Math.random() * 150);
		index3 = (int) (Math.random() * 150);
		Point center1, center2, center3, ncenter1, ncenter2, ncenter3;
		center1 = new Point();
		center1.x = points[index1].x; 
		center1.y = points[index1].y;
		center2 = new Point();
		center2.x = points[index2].x; 
		center2.y = points[index2].y;
		center3 = new Point();
		center3.x = points[index3].x; 
		center3.y = points[index3].y;
		//int c = 0;
		//while(c < 10) {
                while(true) {
			for(i = 0; i < 150; i++) {
				double val1, val2, val3;
				val1 = Math.sqrt(Math.pow(points[i].x - center1.x, 2) + Math.pow(points[i].y - center1.y, 2));
				val2 = Math.sqrt(Math.pow(points[i].x - center2.x, 2) + Math.pow(points[i].y - center2.y, 2));
				val3 = Math.sqrt(Math.pow(points[i].x - center3.x, 2) + Math.pow(points[i].y - center3.y, 2));
				if(val1 < val2 && val1 < val3) points[i].clusterNo = 1;
				else if(val2 < val1 && val2 < val3) points[i].clusterNo = 2;
				else if(val3 < val2 && val3 < val1) points[i].clusterNo = 3;
			}
			// calculate new center points
			int sumxc1 = 0, sumxc2 = 0, sumxc3 = 0, sumyc1 = 0, sumyc2 = 0, sumyc3 = 0;
			int countc1 = 0, countc2 = 0, countc3 = 0;
			for(i = 0; i < 150; i++) {
				if(points[i].clusterNo == 1) { sumxc1 += points[i].x; sumyc1 += points[i].y; countc1++; }
				else if(points[i].clusterNo == 2) { sumxc2 += points[i].x; sumyc2 += points[i].y; countc2++; }
				else if(points[i].clusterNo == 3) { sumxc3 += points[i].x; sumyc3 += points[i].y; countc3++; }
			}
                        ncenter1 = new Point();
                        ncenter2 = new Point();
                        ncenter3 = new Point();
			ncenter1.x = sumxc1 / countc1; ncenter1.y = sumyc1 / countc1;
			ncenter2.x = sumxc2 / countc2; ncenter2.y = sumyc2 / countc2;
			ncenter3.x = sumxc3 / countc3; ncenter3.y = sumyc3 / countc3;
			/*System.out.println("EPOCH " + c);
			System.out.println(center1.x + "\t" + center1.y);
			System.out.println(center2.x + "\t" + center2.y);
			System.out.println(center3.x + "\t" + center3.y);*/
                        if(ncenter1.x == center1.x && ncenter1.y == center1.y && ncenter2.x == center2.x && ncenter2.y == center2.y
                                && ncenter3.x == center3.x && ncenter3.y == center3.y) 
                            break;
                        center1.x = ncenter1.x; center1.y = ncenter1.y;
                        center2.x = ncenter2.x; center2.y = ncenter2.y;
                        center3.x = ncenter3.x; center3.y = ncenter3.y;
			//c++;
		}
                BufferedWriter w = new BufferedWriter(new FileWriter("C:\\Users\\user\\Documents\\NetBeansProjects\\GUIFormExamples\\src\\KMeansImplementation\\cluster_data.csv"));
                for(i = 0; i < 150; i++) {
                    if(points[i].clusterNo == 1) 
                        w.write(points[i].x + "," + points[i].y + "," + points[i].clusterNo + "\n");
                }
                for(i = 0; i < 150; i++) {
                    if(points[i].clusterNo == 2) 
                        w.write(points[i].x + "," + points[i].y + "," + points[i].clusterNo + "\n");
                }
                for(i = 0; i < 150; i++) {
                    if(points[i].clusterNo == 3) 
                        w.write(points[i].x + "," + points[i].y + "," + points[i].clusterNo + "\n");
                }
                w.close();
	}
}
