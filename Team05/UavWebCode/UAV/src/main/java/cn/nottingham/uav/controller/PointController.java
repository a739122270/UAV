package cn.nottingham.uav.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.nottingham.uav.entity.Point;
import cn.nottingham.uav.service.interfaces.IPointService;
import cn.nottingham.uav.util.JsonResult;

/**
 * The controller layer
 * Invoke the IPointService method based on the HTTP request.
 * @author Xuhui Wei
 *
 */
@RestController
@RequestMapping("points")
public class PointController extends BaseController {
	@Autowired
	public IPointService service;

	@PostMapping("add_point")
	public JsonResult<Void> addPoint(String allPathJson, Integer taskId, HttpSession session) {
		System.out.println(allPathJson);
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());
		String username = session.getAttribute(SESSION_USERNAME).toString();
		double[][][] allPath = stringToDouble(allPathJson);
		for (int i = 0; i < allPath.length; i++) { // Parse path string format :"[[[],[]],[[],[]]]"
			for (int m = 0; m < allPath[i].length; m++) {
				Point point = new Point();
				point.setTaskId(taskId);
				point.setAircraftId((int)allPath[i][m][0]);
				point.setLongitude(allPath[i][m][1]);
				point.setLatitude(allPath[i][m][2]);
				point.setPointOrder(m);
				service.addPoint(point, username, userId);
			}
		}
		return new JsonResult<Void>(SUCCESS);
	}

	private double[][][] stringToDouble(String oldStr) {

		String[] strs = oldStr.split("]],");

		double[][][] dd = new double[strs.length][][];

		for (int i = 0; i < strs.length; i++) {
			String strss = strs[i];
			double[][] d = toDoubleArray(strss);
			dd[i] = d;
		}
		return dd;
	}

	private double[][] toDoubleArray(String str) {
		String[] s1 = str.split("],");
		String[] arr = new String[s1.length];
		for (int m = 0; m < s1.length; m++) {
			String temp = deleteCharString(s1[m], '[');
			arr[m] = deleteCharString(temp, ']');
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

	private String deleteCharString(String sourceString, char chElemData) {
		String deleteString = "";
		for (int i = 0; i < sourceString.length(); i++) {
			if (sourceString.charAt(i) != chElemData) {
				deleteString += sourceString.charAt(i);
			}
		}
		return deleteString;
	}
}
