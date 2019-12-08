package test.proxyveris.test;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallerImpl;
import org.audiveris.proxymusic.ScorePartwise;
import org.audiveris.proxymusic.util.Marshalling;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class HelloWorldProxyMusic {

    public static void main(String[] args) {

//        File xmlFile = new File("C:\\UPJŠ\\Bakalárska práca\\SimFolk\\SimFolk\\src\\test\\proxyveris\\test\\zaspievalo_vtaca.xml");
//
//        Unmarshaller unmarshaller = new UnmarshallerImpl();
//
//        try {
//            Marshalling.getContext(ScorePartwise.class);
//            InputStream is = new FileInputStream(xmlFile);
//            ScorePartwise scorePartwise = (ScorePartwise) Marshalling.unmarshal(is);
//            for (ScorePartwise.Part.Measure measure : scorePartwise.getPart().get(0).getMeasure()) {
//                measure.getNoteOrBackupOrForward();
//            }
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (Marshalling.UnmarshallingException e) {
//            e.printStackTrace();
//        }

    }

}
