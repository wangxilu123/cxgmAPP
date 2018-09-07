package com.cxgm.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.cxgm.domain.CouponCode;
import com.cxgm.domain.Order;

public class PoiUtils {

    public static ResponseEntity<byte[]> exportCoupon2Excel(List<CouponCode> emps) {
        HttpHeaders headers = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.创建Excel文档
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建文档摘要
            workbook.createInformationProperties();
            //3.获取文档信息，并配置
            DocumentSummaryInformation dsi = workbook.getDocumentSummaryInformation();
            //3.1文档类别
            dsi.setCategory("优惠券");
            //3.2设置文档管理员
            dsi.setManager("菜鲜果美");
            //3.3设置组织机构
            dsi.setCompany("菜鲜果美");
            //4.获取摘要信息并配置
            SummaryInformation si = workbook.getSummaryInformation();
            //4.1设置文档主题
            si.setSubject("优惠券");
            //4.2.设置文档标题
            si.setTitle("实体优惠券");
            //4.3 设置文档作者
            si.setAuthor("菜鲜果美超市");
            //4.4设置文档备注
            si.setComments("备注信息暂无");
            //创建Excel表单
            HSSFSheet sheet = workbook.createSheet("实体优惠券");
            //创建日期显示格式
            HSSFCellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
            //创建标题的显示样式
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //定义列的宽度
            sheet.setColumnWidth(0, 5 * 256);
            sheet.setColumnWidth(1, 50 * 256);
           
            //5.设置表头
            HSSFRow headerRow = sheet.createRow(0);
            HSSFCell cell0 = headerRow.createCell(0);
            cell0.setCellValue("编号");
            cell0.setCellStyle(headerStyle);
            HSSFCell cell1 = headerRow.createCell(1);
            cell1.setCellValue("优惠码");
            cell1.setCellStyle(headerStyle);
          
            //6.装数据
            for (int i = 0; i < emps.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1);
                CouponCode emp = emps.get(i);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(emp.getCode());
               
            }
            headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", new String("优惠券.xls".getBytes("UTF-8"), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            baos = new ByteArrayOutputStream();
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }
    
    public static ResponseEntity<byte[]> exportOrderExcel(List<Order> emps) {
        HttpHeaders headers = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.创建Excel文档
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建文档摘要
            workbook.createInformationProperties();
            //3.获取文档信息，并配置
            DocumentSummaryInformation dsi = workbook.getDocumentSummaryInformation();
            //3.1文档类别
            dsi.setCategory("订单明细");
            //3.2设置文档管理员
            dsi.setManager("菜鲜果美");
            //3.3设置组织机构
            dsi.setCompany("菜鲜果美");
            //4.获取摘要信息并配置
            SummaryInformation si = workbook.getSummaryInformation();
            //4.1设置文档主题
            si.setSubject("订单明细");
            //4.2.设置文档标题
            si.setTitle("订单明细");
            //4.3 设置文档作者
            si.setAuthor("菜鲜果美超市");
            //4.4设置文档备注
            si.setComments("备注信息暂无");
            //创建Excel表单
            HSSFSheet sheet = workbook.createSheet("订单明细");
            //创建日期显示格式
            HSSFCellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
            //创建标题的显示样式
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //定义列的宽度
            sheet.setColumnWidth(0, 5 * 256);
            sheet.setColumnWidth(1, 50 * 256);
           
            //5.设置表头
            HSSFRow headerRow = sheet.createRow(0);
            HSSFCell cell0 = headerRow.createCell(0);
            cell0.setCellValue("序号");
            cell0.setCellStyle(headerStyle);
            HSSFCell cell1 = headerRow.createCell(1);
            cell1.setCellValue("订单编号");
            cell1.setCellStyle(headerStyle);
            
            HSSFCell cell2 = headerRow.createCell(2);
            cell2.setCellValue("手机号");
            cell2.setCellStyle(headerStyle);
            
            HSSFCell cell3 = headerRow.createCell(3);
            cell3.setCellValue("订单金额");
            cell3.setCellStyle(headerStyle);
            
            HSSFCell cell4 = headerRow.createCell(4);
            cell4.setCellValue("支付方式");
            cell4.setCellStyle(headerStyle);
            
            HSSFCell cell5 = headerRow.createCell(5);
            cell5.setCellValue("下单时间");
            cell5.setCellStyle(headerStyle);
            
            HSSFCell cell6 = headerRow.createCell(6);
            cell6.setCellValue("商品详情");
            cell6.setCellStyle(headerStyle);
            
            //6.装数据
            for (int i = 0; i < emps.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1);
                Order emp = emps.get(i);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(emp.getOrderNum());
                row.createCell(2).setCellValue(emp.getPhone());
                row.createCell(3).setCellValue(emp.getOrderAmount().toString());
                row.createCell(4).setCellValue(emp.getPayType());
                row.createCell(5).setCellValue(DateKit.dateFormat(emp.getOrderTime(),"yyyy-MM-dd HH:mm:ss"));
                row.createCell(6).setCellValue(emp.getOrderProducts());
               
            }
            headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", new String("对账单.xls".getBytes("UTF-8"), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            baos = new ByteArrayOutputStream();
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }
}
