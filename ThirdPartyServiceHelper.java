package com.epam.qa.Helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.testng.Reporter;
import org.testng.SkipException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.epam.qa.Constants;
import com.epam.qa.componets.Component;
import com.epam.qa.configuration.Configuration;

public class ThirdPartyServiceHelper {

	public static ThirdPartyServiceHelper pdpTest1;
	private String formattedAddress;

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public static String sendHTMLRequest(String endpoint) {
		String result = null;
		if (endpoint.startsWith("http://")) {

			try {

				// Send data
				String urlStr = endpoint;

				URL url = new URL(urlStr);
				URLConnection conn = url.openConnection();

				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8"));
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}
				rd.close();
				result = sb.toString();

				if (result.length() < 3) {
					Reporter.log(String.format(Component.NEGATIVE,
							"Test skipped due to service empty response. Service address = "
									+ Constants.THIRD_PARTY_SERVICE_NAME));
					throw new SkipException(
							"Skipped due to service empty response");
				}
			} catch (Exception e) {
				System.out.println("Service is not available!!!!");
			}
		}
		return result;
	}

	public static synchronized void findAllAviability(String productNumber)
			throws IOException {
		// /0298
		// String jsonString1 =
		// "{\"Store_Number\":[\"0308\"],\"Sku_Number\":\"00";
		String jsonString1 = "{\"Store_Number\":[\"0308\",\"0678\", \"0611\",\"0118\",\"0216\",\"0674\",\"0150\",\"0299\",\"0016\"],\"Sku_Number\":\"";
		String jsonString2 = "\",\"Language\":\"E\",\"Banner\":\"CTR\"}";
		String responce = "";
		JSONHelper jsonHelper = new JSONHelper();

		responce = jsonHelper.sendJsonDataToThirdPartyService(
				Constants.THIRD_PARTY_SERVICE_NAME, jsonString1 + productNumber
						+ jsonString2);

		if (responce.equals("") || responce == null) {
			System.out.println("call to store....." + productNumber);
		}
		if (responce.contains("CastingCharge")) {
			System.out.println("CastingCharge....." + productNumber);

		}

		if (responce.contains("TotalEnviroFee")) {
			System.out.println("TotalEnviroFee....." + productNumber);
		}
		if (responce.contains("Promo")) {
			System.out.println("Promo....." + productNumber);
		}
	}

	public static synchronized void findAllAviability(int startBoundary,
			int endBoundary) throws IOException {
		// /0298
		// String jsonString1 =
		// "{\"Store_Number\":[\"0308\"],\"Sku_Number\":\"00";
		String jsonString1 = "{\"Store_Number\":[\"0308\",\"0678\", \"0611\",\"0118\",\"0216\",\"0674\",\"0150\",\"0299\"],\"Sku_Number\":\"0";
		String jsonString2 = "\",\"Language\":\"E\",\"Banner\":\"CTR\"}";
		String responce = "";
		JSONHelper jsonHelper = new JSONHelper();
		for (int i = startBoundary; i <= endBoundary; i++) {
			responce = jsonHelper.sendJsonDataToThirdPartyService(
					Constants.THIRD_PARTY_SERVICE_NAME, jsonString1 + i
							+ jsonString2);

			if (responce.equals("") || responce == null) {
				System.out.println("call to store....." + i);
			}
			if (responce.contains("CastingCharge")) {
				System.out.println("CastingCharge....." + i);
				System.out.println(responce.subSequence(0, 2));
				System.out.print(i);
			}
			if (responce.contains("Quantity")) {
				System.out.println("Quantity....." + i);

			}
			// try {
			// jsonAr = new JSONArray(responce);
			//
			// for (int j = 0; j < jsonAr.length(); j++) {
			// JSONObject item = jsonAr.getJSONObject(j);
			// String quantity = item.getString("Quantity");
			// store = item.getString("Store");
			// sku = item.getString("SKU");
			// if (Integer.parseInt(quantity) != 0) {
			// System.out.println("------No Promo-----------------");
			// System.out.println(jsonAr.getJSONObject(j)
			// .getString("Price"));
			// System.out.println(quantity);
			// System.out.println(store);
			// System.out.println(sku);
			// System.out.println("-----------------------");
			//
			// if (item.getString("Promo") != null) {
			// System.out.println("------Promo-----------------");
			// System.out.println(jsonAr.getJSONObject(j)
			// .getString("Price"));
			// System.out.println(jsonAr.getJSONObject(j)
			// .getJSONObject("Promo").get("Price"));
			// System.out.println(quantity);
			// System.out.println(store);
			// System.out.println(sku);
			//
			// System.out.println("-----------------------");
			// }
			// if (item.getString("TotalEnviroFee") != null) {
			// System.out
			// .println("-----------TotalEnviroFee------------");
			// System.out.println(jsonAr.getJSONObject(j)
			// .getString("TotalEnviroFee"));
			//
			// System.out.println(quantity);
			// System.out.println(store);
			// System.out.println(sku);
			//
			// System.out.println("-----------------------");
			// }
			// if (jsonHelper.getValueByKey("EnviroFeeIncluded",
			// item.toString()) != "null") {
			// System.out
			// .println("-----------TotalEnviroFee------------");
			// System.out.println(quantity);
			// System.out.println(store);
			// System.out.println(sku);
			//
			// System.out.println("-----------------------");
			// }
			// if (jsonHelper.getValueByKey("CastingCharge", item.toString()) !=
			// "null") {
			// System.out
			// .println("-----------CastingCharge------------");
			// System.out.println(quantity);
			// System.out.println(store);
			// System.out.println(sku);
			//
			// System.out.println("-----------------------");
			// }
			// }
			// }
			// } catch (JSONException e) {
			// System.out.println(Thread.currentThread().getName() + "  " + i);
			//
			// }
		}
	}

	public static synchronized String findAllAviability(String storeNumber,
			String sku) throws IOException {
		System.out.println("DEBUG STORE NUMBER" + storeNumber);
		System.out.println("DEBUG SKU NUMBER" + sku);
		String jsonString1 = "{\"Store_Number\":\"" + storeNumber
				+ "\",\"Sku_Number\":\"" + sku
				+ "\",\"Language\":\"E\",\"Banner\":\"CTR\"}";
		JSONHelper jsonHelper = new JSONHelper();
		String res = jsonHelper.sendJsonDataToThirdPartyService(
				Constants.THIRD_PARTY_SERVICE_NAME, jsonString1);
		System.out.println("DEBUG RESPONCE  " + res);
		return res;

	}

	public static void main(String[] args) throws IOException {

		Thread myThready = new Thread(new Runnable() {
			public void run() {
				pdpTest1 = new ThirdPartyServiceHelper();
				try {
					pdpTest1.findAllAviability(001111, 222221);
				} catch (IOException e) {
					System.out.println(Thread.currentThread());
				}

			}
		});
		myThready.start();

		Thread myThready1 = new Thread(new Runnable() {
			public void run() {
				pdpTest1 = new ThirdPartyServiceHelper();
				try {
					pdpTest1.findAllAviability(900000, 999990);
				} catch (IOException e) {
					System.out.println(Thread.currentThread());
				}

			}
		});
		myThready1.start();

		Thread myThready2 = new Thread(new Runnable() {
			public void run() {
				pdpTest1 = new ThirdPartyServiceHelper();
				try {
					pdpTest1.findAllAviability(555560, 666660);
				} catch (IOException e) {
					System.out.println(Thread.currentThread());
				}

			}
		});
		myThready2.start();

		Thread myThready3 = new Thread(new Runnable() {
			public void run() {
				pdpTest1 = new ThirdPartyServiceHelper();
				try {
					pdpTest1.findAllAviability(666670, 777770);
				} catch (IOException e) {
					System.out.println(Thread.currentThread());
				}

			}
		});
		myThready3.start();

		Thread myThread4 = new Thread(new Runnable() {
			public void run() {
				pdpTest1 = new ThirdPartyServiceHelper();
				try {
					pdpTest1.findAllAviability(777780, 888880);
				} catch (IOException e) {
					System.out.println(Thread.currentThread());
				}

			}
		});
		myThread4.start();

		Thread myThread5 = new Thread(new Runnable() {
			public void run() {
				pdpTest1 = new ThirdPartyServiceHelper();
				try {
					pdpTest1.findAllAviability(888890, 199999);
				} catch (IOException e) {
					System.out.println(Thread.currentThread());
				}

			}
		});
		myThread5.start();

	}

	public List<String> getSkuIdsFromService() {

		return null;

	}

	public String getServiceUrl(String currentURL) {
		String serviceHost = Configuration.getConfiguration().getSrServiceEN();
		if (currentURL.contains("fr")) {
			serviceHost = Configuration.getConfiguration().getSrServiceFR();
		}
		String request = serviceHost + "?site=kiosk";
		System.out.println("Request for S&P to find compare button" + request);
		return request;
	}

		// in progress
		public List<String> getPdpURL(String currentURL) throws IOException,
				Exception {
			String requestURL = getServiceUrl(currentURL);
			List<String> pdpLinks = new ArrayList<String>();

			URL link = new URL(requestURL);
			URLConnection connection = link.openConnection();
			
				Document document = parseXMLByUrl(connection.getInputStream());
				NodeList nList = document.getElementsByTagName("field");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					NamedNodeMap attributes = nNode.getAttributes();
					
				      for (int i = 0; i < attributes.getLength(); i++) {
				        Node attribute = attributes.item(i);
				        if(attribute.getNodeValue().equals("pdp-url")){
				        System.out.println(attribute.getNodeName() + "=>" + attribute.getNodeValue());
				        pdpLinks.add(attribute.getParentNode().getNodeValue());
				        break;
				        }
				      }
				      //System.out.println("Node value is: " + nNode.getNodeValue());
				      //System.out.println("pdpLinks == >" + pdpLinks.get(temp));
				
			}
			return pdpLinks;
		}

	
	private static String getTagValue(NodeList list, String name) {
	    for (int i = 0; i < list.getLength(); i++) {
	        Element e = (Element) list.item(i);
	        if (e.getAttribute("name").equals(name)) {
	            return e.getNodeValue();
	        }
	    }
	    return null;
	}
	
	private Document parseXMLByUrl(InputStream stream) throws Exception {
		DocumentBuilderFactory objDocumentBuilderFactory = null;
		DocumentBuilder objDocumentBuilder = null;
		Document doc = null;
		try {
			objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

			doc = objDocumentBuilder.parse(stream);
		} catch (Exception ex) {
			throw ex;
		}
		return doc;
	}
}
