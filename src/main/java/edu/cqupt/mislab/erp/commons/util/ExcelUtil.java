package edu.cqupt.mislab.erp.commons.util;

import edu.cqupt.mislab.erp.commons.aspect.ExcelOperationException;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-10-21 22:20
 * @description excel导入导出工具类
 */
public abstract class ExcelUtil {

    /**
     * 对Excel中的数据进行提取
     * @param multipartFile 前端传来的Excel文件
     * @return 返回一个由表中数据填充而成的二维list
     */
    public static List<List<String>> dataExtraction(MultipartFile multipartFile) {
        List<List<String>> res = new ArrayList<>();

        // 获取工作簿及工作簿中的第一个sheet表
        XSSFWorkbook workbook;
        try {
            workbook = new XSSFWorkbook(multipartFile.getInputStream());
        } catch (IOException e) {
            throw new ExcelOperationException(WebResponseVo.ResponseStatus.SERVICE_UNAVAILABLE);
        }
        Sheet sheet = workbook.getSheetAt(0);

        for(Row row : sheet){
            List<String> rowList = new ArrayList<>();

            // 中间空白格用null填充，尾部空白格需要调用者自己处理
            for(int i = 0; i < row.getLastCellNum(); i++){
                Cell cell = row.getCell(i);
                String content = cell.getStringCellValue();
                rowList.add("".equals(content) ? null : content);
            }

            res.add(rowList);
        }

        // 过滤表头并返回
        res.remove(0);
        return res;
    }
}
