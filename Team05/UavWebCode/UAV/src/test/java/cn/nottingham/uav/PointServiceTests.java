package cn.nottingham.uav;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.nottingham.uav.entity.Point;
import cn.nottingham.uav.service.interfaces.IPointService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PointServiceTests {
	@Autowired
	IPointService service;

	@Test
	public void createTask() {
		try {
			Point point = new Point();
			point.setTaskId(1);
			point.setLatitude(1.2);
			point.setLongitude(2.4);
			point.setPointOrder(1);
			service.addPoint(point, "jack", 1);
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void split() { // oldStr ->split strs ->split strss
		String oldStr = "[[[1,2],[3,4]],[[4,5],[6,7]],[[5,6],[9,19]]]";
		String[] strs = oldStr.split("]],");
		
		double[][][] dd = new double[strs.length][][];
		
		for (int i = 0; i < strs.length; i++) {
			String strss = strs[i];
			double[][] d= toDoubleArray(strss);
			dd[i]=d;
		}
		
		for (int i = 0; i < dd.length; i++) {
			for (int j = 0; j < dd[i].length; j++) {
				System.out.print(dd[i][j][0]);
			}
			
		}
		

	}

	@Test
	public void test() {
		String a = "[[[1,2],[3,4";

		double[][] d = toDoubleArray(a);
		for (int i = 0; i < d.length; i++) {
			System.out.println();
			for (int j = 0; j < d[i].length; j++) {
				System.out.print("   " + d[i][j]);
			}
		}
	}

	double[][] toDoubleArray(String str) {
		String[] s1 = str.split("],");
		String[] arr = new String[s1.length];
		for(int m=0;m<s1.length;m++) {
			String temp= deleteCharString(s1[m],'[');
			arr[m]= deleteCharString(temp,']');
		}

		double[][] d;


		d = new double[arr.length][];

		for (int i = 0; i < arr.length; i++) {
			String[] s2 = arr[i].split(",");
			d[i] = new double[s2.length];
			for (int j = 0; j < s2.length; j++) {
				d[i][j] = Double.parseDouble(s2[j]);
			}
		}
		return d;
	}

	public String deleteCharString(String sourceString, char chElemData) {
		String deleteString = "";
		for (int i = 0; i < sourceString.length(); i++) {
			if (sourceString.charAt(i) != chElemData) {
				deleteString += sourceString.charAt(i);
			}
		}
		return deleteString;
	}

	@Test
	public void deleteCharString() {
		String a="[1]";
		String b = deleteCharString(a, '[');
		System.out.println(b);

	}
}
