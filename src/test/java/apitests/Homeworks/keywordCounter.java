package apitests.Homeworks;

import org.testng.annotations.Test;
import utilities.ExcelUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class keywordCounter {
    @Test
    public void  readExcelFile() throws IOException {

        String cv = "";
        cv = new String(Files.readAllBytes(Paths.get("src/test/resources/cv.txt")));
        System.out.println("==========   Your CV ======== ");
        System.out.println(cv);

        System.out.println("====== For Keywords Evaluation Please look CvKeywords Excel File");

        ExcelUtil keyword= new ExcelUtil("src/test/resources/CvKeywords.xlsx","keyword");
        List<Map<String, String>> dataList = keyword.getDataList();

        int k=0;
        for (Map<String, String> eachKeyword : dataList) {

            String str=eachKeyword.get("Keyword");

            int i = 0;
            Pattern p = Pattern.compile(str);
            Matcher m = p.matcher( cv );
            while (m.find()) {
                i++;
            }
            System.out.println(str+ " " + i + " times " );
            k++;
            keyword.setCellData(i+" times","Howmany",k);
        }

    }


}
