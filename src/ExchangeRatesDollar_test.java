import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ExchangeRatesDollar_test {

	public static void main(String[] args) {

		String VALUTE = "Valute", ID_ATTR = "ID", ID_DOLLAR = "R01235";
		System.out.print("¬ведите число, мес€ц и год в формате dd.mm.yyyy: ");

		Scanner sc = new Scanner(System.in);
		String dateUser = sc.next();

		DateFormat df = new SimpleDateFormat("dd.mm.yyyy");
		Date date = null;
		sc.close();

		try {
			date = df.parse(dateUser);
			DateFormat dfUrl = new SimpleDateFormat("dd/mm/yyyy");

			String url = "https://www.cbr.ru/scripts/XML_daily.asp?date_req=" + dfUrl.format(date);

			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder b = f.newDocumentBuilder();

			try {

				URLConnection urlConnection = new URL(url).openConnection();
				urlConnection.addRequestProperty("Accept", "application/xml");

				Document doc = b.parse(urlConnection.getInputStream());

				if (doc.getDocumentElement().getChildNodes().getLength() == 0) {
					System.out.println("»звините,но на указанную дату курс USD не найден");
					System.exit(0);
				}

				NodeList lst = doc.getElementsByTagName(VALUTE);

				for (int i = 0; i < lst.getLength(); ++i) {
					Node node = lst.item(i);

					if (node.getNodeType() == Node.ELEMENT_NODE
							&& ((Element) node).getAttribute(ID_ATTR).equals(ID_DOLLAR)) {

						System.out.println(node.getLastChild().getTextContent());
					}

				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

	}

}
