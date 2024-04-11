package negocio.comuns.utilitarias;

/**
 *
 * @author flayson
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.ClientAnchor.*;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.IOUtils;

public class AddDimensionedImage {

    public static final int EXPAND_ROW = 1;
    public static final int EXPAND_COLUMN = 2;
    public static final int EXPAND_ROW_AND_COLUMN = 3;
    public static final int OVERLAY_ROW_AND_COLUMN = 7;
    // Modified to support EMU - English Metric Units - used within the OOXML
    // workbooks, this multoplier is used to convert between measurements in
    // millimetres and in EMUs
    private static final int EMU_PER_MM = 36000;

    public void addImageToSheet(String cellNumber, Sheet sheet, Drawing drawing,
            URL imageFile, double reqImageWidthMM, double reqImageHeightMM,
            int resizeBehaviour) {
        // Convert the String into column and row indices then chain the
        // call to the overridden addImageToSheet() method.
        try {
            CellReference cellRef = new CellReference(cellNumber);
            this.addImageToSheet(cellRef.getCol(), cellRef.getRow(), sheet, drawing,
                    imageFile, reqImageWidthMM, reqImageHeightMM, resizeBehaviour);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addImageToSheet(int colNumber, int rowNumber, Sheet sheet, Drawing drawing,
            URL imageFile, double reqImageWidthMM, double reqImageHeightMM,
            int resizeBehaviour) {
        try {
            ClientAnchor anchor;
            ClientAnchorDetail rowClientAnchorDetail;
            ClientAnchorDetail colClientAnchorDetail;
            int imageType;

            // Validate the resizeBehaviour parameter.
            if ((resizeBehaviour != AddDimensionedImage.EXPAND_COLUMN)
                    && (resizeBehaviour != AddDimensionedImage.EXPAND_ROW)
                    && (resizeBehaviour != AddDimensionedImage.EXPAND_ROW_AND_COLUMN)
                    && (resizeBehaviour != AddDimensionedImage.OVERLAY_ROW_AND_COLUMN)) {
                throw new IllegalArgumentException("Invalid value passed to the "
                        + "resizeBehaviour parameter of AddDimensionedImage.addImageToSheet()");
            }

            // Call methods to calculate how the image and sheet should be
            // manipulated to accommodate the image; columns and then rows.
            colClientAnchorDetail = this.fitImageToColumns(sheet, colNumber,
                    reqImageWidthMM, resizeBehaviour);
            rowClientAnchorDetail = this.fitImageToRows(sheet, rowNumber,
                    reqImageHeightMM, resizeBehaviour);

            // Having determined if and how to resize the rows, columns and/or the
            // image, create the ClientAnchor object to position the image on
            // the worksheet. Note how the two ClientAnchorDetail records are
            // interrogated to recover the row/column co-ordinates and any insets.
            // The first two parameters are not used currently but could be if the
            // need arose to extend the functionality of this code by adding the
            // ability to specify that a clear 'border' be placed around the image.
            anchor = sheet.getWorkbook().getCreationHelper().createClientAnchor();

            anchor.setDx1(0);
            anchor.setDy1(0);
            if (colClientAnchorDetail != null) {
                anchor.setDx2(colClientAnchorDetail.getInset());
                anchor.setCol1(colClientAnchorDetail.getFromIndex());
                anchor.setCol2(colClientAnchorDetail.getToIndex());
            }
            if (rowClientAnchorDetail != null) {
                anchor.setDy2(rowClientAnchorDetail.getInset());
                anchor.setRow1(rowClientAnchorDetail.getFromIndex());
                anchor.setRow2(rowClientAnchorDetail.getToIndex());
            }

            // For now, set the anchor type to do not move or resize the
            // image as the size of the row/column is adjusted. This could easily
            // become another parameter passed to the method. Please read the note
            // above regarding the behaviour of image resizing.
            //anchor.setAnchorType(AnchorType.MOVE_AND_RESIZE);

            // Now, add the picture to the workbook. Note that unlike the similar
            // method in the HSSF Examples section, the image type is checked. First,
            // the image files location is identified by interrogating the URL passed
            // to the method, the images type is identified before it is added to the
            // sheet.
            String sURL = imageFile.toString().toLowerCase(Locale.ROOT);
            if (sURL.endsWith(".png")) {
                imageType = Workbook.PICTURE_TYPE_PNG;
            } else if (sURL.endsWith(".jpg") || sURL.endsWith(".jpeg")) {
                imageType = Workbook.PICTURE_TYPE_JPEG;
            } else {
                throw new IllegalArgumentException("Invalid Image file : "
                        + sURL);
            }
            int index = sheet.getWorkbook().addPicture(
                    IOUtils.toByteArray(imageFile.openStream()), imageType);
            drawing.createPicture(anchor, index);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private ClientAnchorDetail fitImageToColumns(Sheet sheet, int colNumber,
            double reqImageWidthMM, int resizeBehaviour) {

        double colWidthMM;
        double colCoordinatesPerMM;
        int pictureWidthCoordinates;
        ClientAnchorDetail colClientAnchorDetail = null;

        // Get the colum's width in millimetres
        colWidthMM = ConvertImageUnits.widthUnits2Millimetres(
                (short) sheet.getColumnWidth(colNumber));

        // Check that the column's width will accommodate the image at the
        // required dimension. If the width of the column is LESS than the
        // required width of the image, decide how the application should
        // respond - resize the column or overlay the image across one or more
        // columns.
        if (colWidthMM < reqImageWidthMM) {

            // Should the column's width simply be expanded?
            if ((resizeBehaviour == AddDimensionedImage.EXPAND_COLUMN)
                    || (resizeBehaviour == AddDimensionedImage.EXPAND_ROW_AND_COLUMN)) {
                // Set the width of the column by converting the required image
                // width from millimetres into Excel's column width units.
                sheet.setColumnWidth(colNumber,
                        ConvertImageUnits.millimetres2WidthUnits(reqImageWidthMM));
                // To make the image occupy the full width of the column, convert
                // the required width of the image into co-ordinates. This value
                // will become the inset for the ClientAnchorDetail class that
                // is then instantiated.
                if (sheet instanceof HSSFSheet) {
                    colWidthMM = reqImageWidthMM;
                    colCoordinatesPerMM = colWidthMM == 0 ? 0
                            : ConvertImageUnits.TOTAL_COLUMN_COORDINATE_POSITIONS / colWidthMM;
                    pictureWidthCoordinates = (int) (reqImageWidthMM * colCoordinatesPerMM);

                } else {
                    pictureWidthCoordinates = (int) reqImageWidthMM * AddDimensionedImage.EMU_PER_MM;
                }
                colClientAnchorDetail = new ClientAnchorDetail(colNumber,
                        colNumber, pictureWidthCoordinates);
            } // If the user has chosen to overlay both rows and columns or just
            // to expand ONLY the size of the rows, then calculate how to lay
            // the image out across one or more columns.
            else if ((resizeBehaviour == AddDimensionedImage.OVERLAY_ROW_AND_COLUMN)
                    || (resizeBehaviour == AddDimensionedImage.EXPAND_ROW)) {
                colClientAnchorDetail = this.calculateColumnLocation(sheet,
                        colNumber, reqImageWidthMM);
            }
        } // If the column is wider than the image.
        else {
            if (sheet instanceof HSSFSheet) {
                // Mow many co-ordinate positions are there per millimetre?
                colCoordinatesPerMM = ConvertImageUnits.TOTAL_COLUMN_COORDINATE_POSITIONS
                        / colWidthMM;
                // Given the width of the image, what should be it's co-ordinate?
                pictureWidthCoordinates = (int) (reqImageWidthMM * colCoordinatesPerMM);
            } else {
                pictureWidthCoordinates = (int) reqImageWidthMM
                        * AddDimensionedImage.EMU_PER_MM;
            }
            colClientAnchorDetail = new ClientAnchorDetail(colNumber,
                    colNumber, pictureWidthCoordinates);
        }
        return (colClientAnchorDetail);
    }

    private ClientAnchorDetail fitImageToRows(Sheet sheet, int rowNumber,
            double reqImageHeightMM, int resizeBehaviour) {
        Row row;
        double rowHeightMM;
        double rowCoordinatesPerMM;
        int pictureHeightCoordinates;
        ClientAnchorDetail rowClientAnchorDetail = null;

        // Get the row and it's height
        row = sheet.getRow(rowNumber);
        if (row == null) {
            // Create row if it does not exist.
            row = sheet.createRow(rowNumber);
        }

        // Get the row's height in millimetres
        rowHeightMM = row.getHeightInPoints() / ConvertImageUnits.POINTS_PER_MILLIMETRE;

        // Check that the row's height will accommodate the image at the required
        // dimensions. If the height of the row is LESS than the required height
        // of the image, decide how the application should respond - resize the
        // row or overlay the image across a series of rows.
        if (rowHeightMM < reqImageHeightMM) {
            if ((resizeBehaviour == AddDimensionedImage.EXPAND_ROW)
                    || (resizeBehaviour == AddDimensionedImage.EXPAND_ROW_AND_COLUMN)) {
                row.setHeightInPoints((float) (reqImageHeightMM
                        * ConvertImageUnits.POINTS_PER_MILLIMETRE));
                if (sheet instanceof HSSFSheet) {
                    rowHeightMM = reqImageHeightMM;
                    rowCoordinatesPerMM = rowHeightMM == 0 ? 0
                            : ConvertImageUnits.TOTAL_ROW_COORDINATE_POSITIONS / rowHeightMM;
                    pictureHeightCoordinates = (int) (reqImageHeightMM
                            * rowCoordinatesPerMM);
                } else {
                    pictureHeightCoordinates = (int) (reqImageHeightMM
                            * AddDimensionedImage.EMU_PER_MM);
                }
                rowClientAnchorDetail = new ClientAnchorDetail(rowNumber,
                        rowNumber, pictureHeightCoordinates);
            } // If the user has chosen to overlay both rows and columns or just
            // to expand ONLY the size of the columns, then calculate how to lay
            // the image out ver one or more rows.
            else if ((resizeBehaviour == AddDimensionedImage.OVERLAY_ROW_AND_COLUMN)
                    || (resizeBehaviour == AddDimensionedImage.EXPAND_COLUMN)) {
                rowClientAnchorDetail = this.calculateRowLocation(sheet,
                        rowNumber, reqImageHeightMM);
            }
        } // Else, if the image is smaller than the space available
        else {
            if (sheet instanceof HSSFSheet) {
                rowCoordinatesPerMM = ConvertImageUnits.TOTAL_ROW_COORDINATE_POSITIONS
                        / rowHeightMM;
                pictureHeightCoordinates = (int) (reqImageHeightMM * rowCoordinatesPerMM);
            } else {
                pictureHeightCoordinates = (int) (reqImageHeightMM
                        * AddDimensionedImage.EMU_PER_MM);
            }
            rowClientAnchorDetail = new ClientAnchorDetail(rowNumber,
                    rowNumber, pictureHeightCoordinates);
        }
        return (rowClientAnchorDetail);
    }

    private ClientAnchorDetail calculateColumnLocation(Sheet sheet,
            int startingColumn,
            double reqImageWidthMM) {
        ClientAnchorDetail anchorDetail;
        double totalWidthMM = 0.0D;
        double colWidthMM = 0.0D;
        double overlapMM;
        double coordinatePositionsPerMM;
        int toColumn = startingColumn;
        int inset;

        // Calculate how many columns the image will have to
        // span in order to be presented at the required size.
        while (totalWidthMM < reqImageWidthMM) {
            colWidthMM = ConvertImageUnits.widthUnits2Millimetres(
                    (short) (sheet.getColumnWidth(toColumn)));
            // Note use of the cell border width constant. Testing with an image
            // declared to fit exactly into one column demonstrated that it's
            // width was greater than the width of the column the POI returned.
            // Further, this difference was a constant value that I am assuming
            // related to the cell's borders. Either way, that difference needs
            // to be allowed for in this calculation.
            totalWidthMM += (colWidthMM + ConvertImageUnits.CELL_BORDER_WIDTH_MILLIMETRES);
            toColumn++;
        }
        // De-crement by one the last column value.
        toColumn--;
        // Highly unlikely that this will be true but, if the width of a series
        // of columns is exactly equal to the required width of the image, then
        // simply build a ClientAnchorDetail object with an inset equal to the
        // total number of co-ordinate positions available in a column, a
        // from column co-ordinate (top left hand corner) equal to the value
        // of the startingColumn parameter and a to column co-ordinate equal
        // to the toColumn variable.
        //
        // Convert both values to ints to perform the test.
        if ((int) totalWidthMM == (int) reqImageWidthMM) {
            // A problem could occur if the image is sized to fit into one or
            // more columns. If that occurs, the value in the toColumn variable
            // will be in error. To overcome this, there are two options, to
            // ibcrement the toColumn variable's value by one or to pass the
            // total number of co-ordinate positions to the third paramater
            // of the ClientAnchorDetail constructor. For no sepcific reason,
            // the latter option is used below.
            if (sheet instanceof HSSFSheet) {
                anchorDetail = new ClientAnchorDetail(startingColumn,
                        toColumn, ConvertImageUnits.TOTAL_COLUMN_COORDINATE_POSITIONS);
            } else {
                anchorDetail = new ClientAnchorDetail(startingColumn,
                        toColumn, (int) reqImageWidthMM * AddDimensionedImage.EMU_PER_MM);
            }
        } // In this case, the image will overlap part of another column and it is
        // necessary to calculate just how much - this will become the inset
        // for the ClientAnchorDetail object.
        else {
            // Firstly, claculate how much of the image should overlap into
            // the next column.
            overlapMM = reqImageWidthMM - (totalWidthMM - colWidthMM);

            // When the required size is very close indded to the column size,
            // the calcaulation above can produce a negative value. To prevent
            // problems occuring in later caculations, this is simply removed
            // be setting the overlapMM value to zero.
            if (overlapMM < 0) {
                overlapMM = 0.0D;
            }

            if (sheet instanceof HSSFSheet) {
                // Next, from the columns width, calculate how many co-ordinate
                // positons there are per millimetre
                coordinatePositionsPerMM = (colWidthMM == 0) ? 0
                        : ConvertImageUnits.TOTAL_COLUMN_COORDINATE_POSITIONS / colWidthMM;
                // From this figure, determine how many co-ordinat positions to
                // inset the left hand or bottom edge of the image.
                inset = (int) (coordinatePositionsPerMM * overlapMM);
            } else {
                inset = (int) overlapMM * AddDimensionedImage.EMU_PER_MM;
            }

            // Now create the ClientAnchorDetail object, setting the from and to
            // columns and the inset.
            anchorDetail = new ClientAnchorDetail(startingColumn, toColumn, inset);
        }
        return (anchorDetail);
    }

    private ClientAnchorDetail calculateRowLocation(Sheet sheet,
            int startingRow, double reqImageHeightMM) {
        ClientAnchorDetail clientAnchorDetail;
        Row row;
        double rowHeightMM = 0.0D;
        double totalRowHeightMM = 0.0D;
        double overlapMM;
        double rowCoordinatesPerMM;
        int toRow = startingRow;
        int inset;

        // Step through the rows in the sheet and accumulate a total of their
        // heights.
        while (totalRowHeightMM < reqImageHeightMM) {
            row = sheet.getRow(toRow);
            // Note, if the row does not already exist on the sheet then create
            // it here.
            if (row == null) {
                row = sheet.createRow(toRow);
            }
            // Get the row's height in millimetres and add to the running total.
            rowHeightMM = row.getHeightInPoints()
                    / ConvertImageUnits.POINTS_PER_MILLIMETRE;
            totalRowHeightMM += rowHeightMM;
            toRow++;
        }
        // Owing to the way the loop above works, the rowNumber will have been
        // incremented one row too far. Undo that here.
        toRow--;
        // Check to see whether the image should occupy an exact number of
        // rows. If so, build the ClientAnchorDetail record to point
        // to those rows and with an inset of the total number of co-ordinate
        // position in the row.
        //
        // To overcome problems that can occur with comparing double values for
        // equality, cast both to int(s) to truncate the value; VERY crude and
        // I do not really like it!!
        if ((int) totalRowHeightMM == (int) reqImageHeightMM) {
            if (sheet instanceof HSSFSheet) {
                clientAnchorDetail = new ClientAnchorDetail(startingRow, toRow,
                        ConvertImageUnits.TOTAL_ROW_COORDINATE_POSITIONS);
            } else {
                clientAnchorDetail = new ClientAnchorDetail(startingRow, toRow,
                        (int) reqImageHeightMM * AddDimensionedImage.EMU_PER_MM);
            }
        } else {
            // Calculate how far the image will project into the next row. Note
            // that the height of the last row assessed is subtracted from the
            // total height of all rows assessed so far.
            overlapMM = reqImageHeightMM - (totalRowHeightMM - rowHeightMM);

            // To prevent an exception being thrown when the required width of
            // the image is very close indeed to the column size.
            if (overlapMM < 0) {
                overlapMM = 0.0D;
            }

            if (sheet instanceof HSSFSheet) {
                rowCoordinatesPerMM = (rowHeightMM == 0) ? 0
                        : ConvertImageUnits.TOTAL_ROW_COORDINATE_POSITIONS / rowHeightMM;
                inset = (int) (overlapMM * rowCoordinatesPerMM);
            } else {
                inset = (int) overlapMM * AddDimensionedImage.EMU_PER_MM;
            }
            clientAnchorDetail = new ClientAnchorDetail(startingRow,
                    toRow, inset);
        }
        return (clientAnchorDetail);
    }
//    public static void main(String[] args) throws IOException {
////        if (args.length < 2) {
////            System.err.println("Usage: AddDimensionedImage imageFile outputFile");
////            return;
////        }
//
////        final String imageFile = "/home/develop/MSI/projetos-svn/PROGRAMA/MSI-1.21.0308/build/web/imagens/imagemRelatorio262605360001-30.jpg";
////        final String outputFile = "/home/develop/teste.xls";
////
////        try {
////            Workbook workbook = new HSSFWorkbook();
////            final FileOutputStream fos = new FileOutputStream(outputFile);
////            Sheet sheet = workbook.createSheet("Picture Test");
////            new AddDimensionedImage().addImageToSheet("A1", sheet, sheet.createDrawingPatriarch(),
////                    new File(imageFile).toURI().toURL(), 20, 20,
////                    AddDimensionedImage.EXPAND_ROW_AND_COLUMN);
////            workbook.write(fos);
////        } catch (Exception ex) {   // OR XSSFWorkbook
////        }
//    }
}
