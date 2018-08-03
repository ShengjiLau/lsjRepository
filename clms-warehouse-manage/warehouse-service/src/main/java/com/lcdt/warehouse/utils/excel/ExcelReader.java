package com.lcdt.warehouse.utils.excel;

import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelReader {


    public static void readSheet(Sheet sheet,CellReadCallback callback){
        if (sheet == null) {
            return;
        }

        sheet.rowIterator().forEachRemaining(row -> {

            row.cellIterator().forEachRemaining(cell -> callback.onCellRead(cell));
        });
    }


    public  interface CellReadCallback {
        void onCellRead(Cell cell);
    }

}
