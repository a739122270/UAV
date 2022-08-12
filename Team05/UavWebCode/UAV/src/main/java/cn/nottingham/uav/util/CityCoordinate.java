package cn.nottingham.uav.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class CityCoordinate {
	public static String[] getCityCoordinate(String cityName) throws IOException {
			String[] coordinate = new CityCoordinate().getCoordinate(cityName);
			return coordinate;
	}
	/**
	 * look up the latitude and longitude by the city name
	 * the address of the @param addr query
	 * @return
	 * @throws IOException
	 */
	public String[] getCoordinate(String addr) throws IOException {
		String lng = null;
		String lat = null;
		String address = null;
		try {
			address = java.net.URLEncoder.encode(addr, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String key = "NcMnc56RX48MjpsOfP4ZEW5GVHmCCmeg";
		String url = String.format("http://api.map.baidu.com/geocoder?address=null&output=json&key=%s&city=%s", key, address);
		URL myURL = null;
		URLConnection httpsConn = null;
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		InputStreamReader insr = null;
		BufferedReader br = null;
		try {
			httpsConn = (URLConnection) myURL.openConnection();// no proxy is used
			if (httpsConn != null) {
				insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
				br = new BufferedReader(insr);
				String data = null;
				int count = 1;
				while ((data = br.readLine()) != null) {
					if (count == 5) {
						lng = (String) data.subSequence(data.indexOf(":") + 1, data.indexOf(","));
						count++;
					} else if (count == 6) {
						lat = data.substring(data.indexOf(":") + 1);
						count++;
					} else {
						count++;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (insr != null) {
				insr.close();
			}
			if (br != null) {
				br.close();
			}
		}
		return new String[] { lng, lat };
	}

}
