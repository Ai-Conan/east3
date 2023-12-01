package com.project.controller;

import com.itextpdf.text.*;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.project.pojo.PageBean;
import com.project.pojo.Result;
import com.project.pojo.kecheng;
import com.project.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 员工管理Controller
 */
@Slf4j
@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;


    //查询全部课程
    @GetMapping("{no}/course")
    public Result selectAllCourse(@PathVariable Integer no) {
        log.info("查询, {}", no);
        List<Map<String, Object>> select = courseService.selectAllCourse(no);
        return Result.success(select);
    }

    //查询单个课程
  /*  @GetMapping("{no}/course")
    public Result selectCourse(@PathVariable Integer no,@PathVariable String coursename) {
        log.info("查询, {},{}", no,coursename);
        List<Map<String, Object>> select = courseService.selectCourse(no,coursename);
        return Result.success(select);
    }
   */

    @GetMapping("{no}/courseWhatt/{courseName}")
    public Result selectClass(@PathVariable Integer no,@PathVariable String courseName) {
        log.info("查找全部班级: {}", no);
        List<String> Class=courseService.selectClass(courseName, no);
        return Result.success(Class);
    }


    @GetMapping("{no}/course/{courseName}/{clas_s}")
    public Result page(@PathVariable String courseName, @RequestParam(defaultValue = "1") Integer page, @PathVariable Integer no, @PathVariable Integer clas_s) {
        log.info("分页查询, 参数: {},{}", no, clas_s);
        //调用service分页查询
        PageBean pageBean = courseService.page(courseName, page, no, clas_s);
        return Result.success(pageBean);
    }


    @GetMapping("{no}/course/{courseName}/{clas_s}/delete")
    public Result delete(@PathVariable String courseName, @PathVariable Integer no,@PathVariable Integer clas_s) {
        log.info("批量删除操作, {}，{}", no, clas_s);
        courseService.delete(courseName, no, clas_s);
        return Result.success();
    }


    @GetMapping("{no}/course/{courseName}/{clas_s}/excel")
    public void downloadAllClassmate(HttpServletResponse response, @PathVariable String courseName, @PathVariable Integer no, @PathVariable Integer clas_s) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(); // 创建HSSFWorkbook对象, excel的文档对象
        HSSFSheet sheet = workbook.createSheet("信息表"); // excel的表单

        List<kecheng> classmateList = courseService.courseinfor(courseName, no, clas_s);

        String fileName = "student" + ".xls"; // 设置要导出的文件的名字
        // 新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = { "学号", "姓名", "专业", "考勤次数", "出勤率" };
        // headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        // 在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 在表中存放查询到的数据放入对应的列
        for (kecheng course : classmateList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            HSSFCell cell0 = row1.createCell(0);
            HSSFCellStyle textStyle = workbook.createCellStyle();
            HSSFDataFormat format = workbook.createDataFormat();
            textStyle.setDataFormat(format.getFormat("@"));
            cell0.setCellStyle(textStyle);
            cell0.setCellValue(course.getId()); // 设置学号为字符串格式
            row1.createCell(1).setCellValue(course.getName());
            row1.createCell(2).setCellValue(course.getMajor());
            row1.createCell(3).setCellValue(String.format("%d/%d", course.getTotalClasses() - course.getAbsentCount(), course.getTotalClasses()));
            row1.createCell(4).setCellValue(course.getAbsentRate());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }




    @GetMapping("{no}/course/{courseName}/{clas_s}/pdf")
    public void downloadAllClassmateAsPdf(HttpServletResponse response, @PathVariable String courseName, @PathVariable Integer no, @PathVariable Integer clas_s) throws IOException {
        // 创建一个新的PDF文档
        Document document = new Document();

        // 设置文件名
        String fileName = "student.pdf";

        ByteArrayOutputStream baos = null;
        try {
            // 导入中文字体文件
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            // 创建PDF写入器对象
            baos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            // 打开PDF文档
            document.open();
            // 创建一个新的PDF表格
            PdfPTable table = new PdfPTable(5); // 6列，与Excel表格的列数对应
            // 设置表格宽度占比
            table.setWidthPercentage(100);
            // 定义表头字体和颜色
            Font headerFont = new Font(bfChinese, 12, Font.BOLD, BaseColor.BLACK);
            // 表头
            String[] headers = {"学号", "姓名", "专业", "考勤次数", "出勤率"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(BaseColor.GRAY);
                table.addCell(cell);
            }
            // 查询数据并填充表格
            List<kecheng> classmateList = courseService.courseinfor(courseName, no, clas_s);
            // 设置表格字体
            Font cellFont = new Font(bfChinese, 11, Font.NORMAL);
            // 填充表格数据
            for (kecheng teacher : classmateList) {
                table.addCell(new PdfPCell(new Phrase(String.valueOf(teacher.getId()), cellFont)));
                table.addCell(new PdfPCell(new Phrase(teacher.getName(), cellFont)));
                table.addCell(new PdfPCell(new Phrase(teacher.getMajor(), cellFont)));
                table.addCell(new PdfPCell(new Phrase(String.format("%d/%d", teacher.getTotalClasses() - teacher.getAbsentCount(), teacher.getTotalClasses()), cellFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(teacher.getAbsentRate()), cellFont)));
            }
            // 将表格添加到PDF文档中
            document.add(table);
        } catch (DocumentException e) {
            // 处理异常
            e.printStackTrace();
        } finally {
            // 关闭PDF文档
            document.close();
        }

        // 设置响应头，将文件名编码为UTF-8，避免乱码
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        // 将 PDF 文件的字节流数据写入响应
        OutputStream os = response.getOutputStream();
        if (baos != null) {
            baos.writeTo(os);
        }
        os.flush();
        os.close();
    }



}






