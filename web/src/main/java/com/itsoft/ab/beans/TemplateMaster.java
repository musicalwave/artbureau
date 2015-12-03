package com.itsoft.ab.beans;

import com.itsoft.ab.model.TeacherDataModel;
import com.itsoft.ab.model.TeacherModel;
import com.itsoft.ab.persistence.TeacherDataMapper;
import com.itsoft.ab.persistence.TeacherMapper;
import com.itsoft.ab.sys.TokenReplacer;
import org.apache.log4j.Logger;
import org.docx4j.XmlUtils;
import org.docx4j.convert.out.pdf.PdfConversion;
import org.docx4j.convert.out.pdf.viaXSLFO.Conversion;
import org.docx4j.convert.out.pdf.viaXSLFO.PdfSettings;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.org.xhtmlrenderer.util.IOUtil;
import org.docx4j.wml.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Павел
 * Date: 09.04.14
 * Time: 2:48
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TemplateMaster {

    Logger LOG = Logger.getLogger(TemplateMaster.class);

    @Value("classpath:word/Shablon_Akt.docx")
    private java.io.File template;

    @Autowired
    private TeacherDataMapper teacherMapper;

    public void fillTemplate(OutputStream os, String startdate, String finishdate,
                             int soloquantity, int groupquantity,
                             int pairquantity, int ensemblequantity,
                             int solorate, int grouprate, int pairrate,
                             int ensemblerate, int teacherid, String actnumber) {
        try {
            Map<String, String> map = new HashMap<String, String>();
            TeacherDataModel teacher = teacherMapper.getDataByTeacherId(teacherid);
            map.put("actnumber", actnumber);
            map.put("fromdate", transformDate(startdate));
            map.put("finishdate", transformDate(finishdate));
            map.put("customerfull", teacher.getLname() + " " + teacher.getFname() + " " + teacher.getPname());
            map.put("soloquantity", new Integer(soloquantity).toString());
            map.put("pairquantity",new Integer( pairquantity).toString());
            map.put("groupquantity", new Integer(groupquantity).toString());
            map.put("ensemblequantity", new Integer(ensemblequantity).toString());
            map.put("solosumm", new Integer(solorate * soloquantity).toString());
            map.put("groupsumm", new Integer(grouprate * groupquantity).toString());
            map.put("pairsumm", new Integer(pairrate * pairquantity).toString());
            map.put("ensemblesumm", new Integer(ensemblerate * ensemblequantity).toString());
            int total = solorate * soloquantity + grouprate * groupquantity
                    + pairrate * pairquantity + ensemblerate * ensemblequantity;
            map.put("total", new Integer(total).toString());
            map.put("tax", new Float(total * 0.13f).toString());
            map.put("totalpay", new Float(total * 0.87f).toString());
            map.put("customershort", teacher.getFname().substring(0,1) + "." +
                    teacher.getPname().substring(0,1) + " " + teacher.getLname());

            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(template);
            MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();
            org.docx4j.wml.Document wmlDoc = mdp.getJaxbElement();
            String text = XmlUtils.marshaltoString(wmlDoc, false);

            TokenReplacer tr = new TokenReplacer();
            String result = tr.replaceTokens(text, map);
            mdp.setJaxbElement((Document) XmlUtils.unmarshalString(result));

            SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
            saver.save(os);

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e);
        }

    }

    private String transformDate(String date) {
        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date d = df.parse(date);
            String month;
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            switch (cal.get(Calendar.MONTH)) {
                case 1: month = "Января"; break;
                case 2: month = "Февряля"; break;
                case 3: month = "Марта"; break;
                case 4: month = "Апреля"; break;
                case 5: month = "Мая"; break;
                case 6: month = "Июня"; break;
                case 7: month = "Июля"; break;
                case 8: month = "Августа"; break;
                case 9: month = "Сентября"; break;
                case 10: month = "Октября"; break;
                case 11: month = "Ноября"; break;
                case 12: month = "Декабря"; break;
                default: month = ""; break;
            }
            String result = cal.get(Calendar.DAY_OF_MONTH) + " " + month + " " + cal.get(Calendar.YEAR);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public void setTemplate(File template) {
        this.template = template;
    }

}
