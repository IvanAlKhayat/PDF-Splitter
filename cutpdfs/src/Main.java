import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import com.itextpdf.kernel.utils.PdfMerger;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Document;


public class Main {
    public static int wantedHeight=1100;

    public static void main(String ...args) throws IOException {
        System.out.println("gimme the WHOLE path where you have your pdfs to convert");
        Scanner in = new Scanner(System.in);

        String newDownloadPath= in.nextLine();
        List<File> files=Arrays.asList(new File(newDownloadPath).listFiles());
        for(final File file : files){
        //try {
            //File file = new File("/Users/ivanalkhayat/Downloads/28_Samples.pdf");
            //if(!file.exists()){
            //    file.mkdir();
            //}
            PDDocument doc= PDDocument.load(file);
            PDPage mySinglePage = doc.getPage(0);
            int height= (int) Math.ceil(mySinglePage.getMediaBox().getHeight());
            int width= (int) Math.ceil(mySinglePage.getMediaBox().getWidth());
            print("la width:",width,
                    " e la height:",height);

            int sizecounter= height;
            int i=0;
            File f1= new File("/Users/ivanalkhayat/Downloads/prob");

            for(File ff: Objects.requireNonNull(f1.listFiles()))
                if (!ff.isDirectory())
                    ff.delete();


            while(sizecounter>0){
                mySinglePage.setCropBox(new PDRectangle(0, sizecounter, width, wantedHeight));
                sizecounter= sizecounter-wantedHeight+80;//just to repeat
                doc.save("/Users/ivanalkhayat/Downloads/"+file.getName()+"_part_"+String.valueOf(i)+".pdf");
                i++;
            }



            List<InputStream> list = new ArrayList();

            //output pdf files

         //   File[] listfiles=(new File("/Users/ivanalkhayat/Downloads/prob").listFiles());
         //   Arrays.sort((listfiles));
         //   for  (File f : listfiles){
         //       if(f.getName().endsWith(".pdf"))
         //           list.add(new FileInputStream(f));
//
         //   }

            try {

                OutputStream out = new FileOutputStream(new File("/Users/ivanalkhayat/Downloads/final.pdf"));
            }catch (Exception e){
                e.printStackTrace();
            }

            //mergeFiles(list, out);


    }
    }
    public static void mergeFiles(List<InputStream> list, OutputStream outputStream)
            throws DocumentException, IOException {
        Document document = new com.itextpdf.text.Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();

        for (InputStream in : list) {
            PdfContentByte cb = writer.getDirectContent();

            print("nuovo giro");
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                PdfImportedPage page = writer.getImportedPage(reader, i);
                cb.addTemplate(page, 0, 0);
            }
            outputStream.flush();

        }

        document.close();
        outputStream.close();
    }


public static void print(Object ...args){
        System.out.println(Arrays.toString(args));
}

}
