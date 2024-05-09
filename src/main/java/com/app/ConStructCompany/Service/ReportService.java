package com.app.ConStructCompany.Service;

import com.app.ConStructCompany.Entity.Product;
import com.app.ConStructCompany.Repository.ProductRepository;
import com.app.ConStructCompany.Request.dto.ImportOrderDto;
import com.app.ConStructCompany.Request.dto.ImportStatisticDetailDto;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReportService {
    private final ProductRepository productRepository;
    public List<ImportStatisticDetailDto> importReport(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        List<ImportStatisticDetailDto> tableDataList = new ArrayList<>();

        int startRow = 1;
        int lastRow = sheet.getLastRowNum();
        for (int rowNum = startRow; rowNum <= lastRow; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (isEmptyRow(row, 6)){
                break;
            }
            ImportStatisticDetailDto dto = new ImportStatisticDetailDto();
            dto.setDate(getCellValue(row.getCell(0)));
            dto.setNumber(getCellValue(row.getCell(1)));
            dto.setLicensePlate(getCellValue(row.getCell(2)));
            dto.setTrailersLicensePlate(getCellValue(row.getCell(3)));
            dto.setTypeProduct(getCellValue(row.getCell(4)));
            if (!StringUtils.isEmpty(getCellValue(row.getCell(4)))){
                Optional<Product> product = productRepository.findByProNameAndDeletedFalse(getCellValue(row.getCell(4)));
                if (!product.isEmpty()){
                    dto.setProductId(product.get().getId());
                }
            }
            if (!StringUtils.isEmpty(getCellValue(row.getCell(5)))) {
                dto.setMaterialWeight(Double.parseDouble(getCellValue(row.getCell(5))));
            } else {
                dto.setMaterialWeight(0.0);
            }
            if (!StringUtils.isEmpty(getCellValue(row.getCell(6)))) {
                dto.setPrice(Double.parseDouble(getCellValue(row.getCell(6))));
            } else {
                dto.setPrice(0.0);
            }
            tableDataList.add(dto);
        }
        return tableDataList;
    }

    public List<ImportOrderDto> importOrder(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        List<ImportOrderDto> tableDataList = new ArrayList<>();

        int startRow = 0;
        int lastRow = sheet.getLastRowNum();
        for (int rowNum = startRow; rowNum <= lastRow; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (isEmptyRow(row, 3)){
                break;
            }
            ImportOrderDto dto = new ImportOrderDto();
            dto.setName(getCellValue(row.getCell(0)));
            if (!StringUtils.isEmpty(getCellValue(row.getCell(0)))){
                Optional<Product> product = productRepository.findByProNameAndDeletedFalse(getCellValue(row.getCell(0)));
                if (!product.isEmpty()){
                    dto.setProductId(product.get().getId());
                }
            }
            dto.setUnit(getCellValue(row.getCell(1)));
            if (!StringUtils.isEmpty(getCellValue(row.getCell(2)))) {
                dto.setMaterialWeight(Double.parseDouble(getCellValue(row.getCell(2))));
            } else {
                dto.setMaterialWeight(0.0);
            }
            tableDataList.add(dto);
        }
        return tableDataList;
    }

    private Boolean isEmptyRow(Row row, int quantity) {
        for (int i = row.getFirstCellNum(); i <= quantity; i++) {
            if (!ObjectUtils.isEmpty(getCellValue(row.getCell(i)))) {
                return false;
            }
        }
        return true;
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case FORMULA:
                    FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                    CellValue cellEval = evaluator.evaluate(cell);
                    if (cellEval.getCellType() == CellType.NUMERIC) {
                        cellValue = String.valueOf(cellEval.getNumberValue());
                    } else if (cellEval.getCellType() == CellType.STRING) {
                        cellValue = cellEval.getStringValue();
                    }
                    break;
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        cellValue = sdf.format(date);
                    } else {
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                default:
                    break;
            }
        }
        return cellValue;
    }
}
