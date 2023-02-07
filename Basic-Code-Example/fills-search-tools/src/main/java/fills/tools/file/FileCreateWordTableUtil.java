package fills.tools.file;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSignedTwipsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGrid;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGridCol;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTextScale;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHeightRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

/**
 * 
 * @ClassName: FileCreateWordTableUtil.java
 * @Description: 生成Table-word文档
 * @author: ysf
 * @date: 2021年1月22日 下午2:19:07 
 *
 * Modification History:
 * Date         Author         Description
 * 2021年1月22日      ysf            生成Table-word文档
 */
public class FileCreateWordTableUtil {
	
	
	/**
	 * 
	 * @Function: FileCreateWordTableUtil.java
	 * @Description: 生成word表格
	 *
	 * @param: xdoc word文档
	 * @param: column_widths 列宽
	 * @param: FILEDS  字段列表内容
	 * @param: list 生成表格源数据
	 * @param: tableName 表名
	 * @param: fintSize 表格内容字体大小
	 * @param:@throws Exception
	 * @return：void
	 *
	 * @author: ysf
	 * @date: 2021年1月22日 下午2:22:52 
	 *
	 * Modification History:
	 * Date         Author      Description
	 * 2021年1月26日      ysf         生成word表格
	 */
	public static void createTable(XWPFDocument xdoc,int[] column_widths,String[] FILEDS,List<String[]> list,String tableName,String fintSize) throws Exception {
		XWPFParagraph p = xdoc.createParagraph();
		setParagraphSpacingInfo(p, true, "0", "80", null, null, true, "500", STLineSpacingRule.EXACT);
		setParagraphAlignInfo(p, ParagraphAlignment.CENTER,TextAlignment.CENTER);
		XWPFRun pRun = getOrAddParagraphFirstRun(p, false, false);
		/**设置标题头 start*/
		setParagraphRunFontInfo(p, pRun, tableName, "宋体", "Times New Roman", "36", true, false, false, false, null, null, 0, 0, 90);
		p = xdoc.createParagraph();
		setParagraphSpacingInfo(p, true, "0", "80", null, null, true, "500", STLineSpacingRule.EXACT);
		setParagraphAlignInfo(p, ParagraphAlignment.CENTER, TextAlignment.CENTER);
		pRun = getOrAddParagraphFirstRun(p, false, false);
		/**end*/
	
		XWPFTable table = xdoc.createTable(list.size()+1, FILEDS.length);
		setTableBorders(table, STBorder.SINGLE, "4", "auto", "0");
		setTableWidthAndHAlign(table, "9024", STJc.CENTER);
		setTableCellMargin(table, 0, 108, 0, 108);
		setTableGridCol(table, column_widths);
		XWPFTableRow row = table.getRow(0);
		setRowHeight(row, "460", STHeightRule.AT_LEAST);
		XWPFTableCell cell = row.getCell(0);
		setCellShdStyle(cell, true, "FFFFFF", null);
		p = getCellFirstParagraph(cell);
		pRun = getOrAddParagraphFirstRun(p, false, false);
		int index = 0;
		//添加Tag
		row = table.getRow(index);
		setRowHeight(row, "567", STHeightRule.AT_LEAST);
		//创建Tag
		for(int i=0;i<FILEDS.length;i++){
			cell = row.getCell(i);
			setCellWidthAndVAlign(cell, String.valueOf(column_widths[i]), STTblWidth.DXA, STVerticalJc.TOP);
			p = getCellFirstParagraph(cell);
			pRun = getOrAddParagraphFirstRun(p, false, false);
			setParagraphRunFontInfo(p, pRun, FILEDS[i], "宋体", "Times New Roman", "21", false, false, false, false, null, null, 0, 6, 0);
		}
		index = 1;
		for(String[] str : list){
			row = table.getRow(index);
			for(int i=0;i<FILEDS.length;i++){
				cell = row.getCell(i);
				setCellWidthAndVAlign(cell, String.valueOf(column_widths[i]), STTblWidth.DXA, STVerticalJc.TOP);
				p = getCellFirstParagraph(cell);
				pRun = getOrAddParagraphFirstRun(p, false, false);
				setParagraphRunFontInfo(p, pRun, str[i], "宋体", "Times New Roman", fintSize, false, false, false, false, null, null, 0, 6, 0);
			}
			index++;
		}
		setParagraphRunFontInfo(p, pRun, "", "宋体", "Times New Roman", "36", true, false, false, false, null, null, 0, 0, 90);
		p = xdoc.createParagraph();
		setParagraphSpacingInfo(p, true, "0", "0", "0", "0", true, "240", STLineSpacingRule.AUTO);
		setParagraphAlignInfo(p, ParagraphAlignment.LEFT, TextAlignment.CENTER);
		pRun = getOrAddParagraphFirstRun(p, false, false);
		System.out.println("创建【"+tableName+"】成功！");
	}
	
	/**
	 * @Description: 设置Table的边框
	 */
	public static void setTableBorders(XWPFTable table, STBorder.Enum borderType,
			String size, String color, String space) {
		CTTblPr tblPr = getTableCTTblPr(table);
		CTTblBorders borders = tblPr.isSetTblBorders() ? tblPr.getTblBorders()
				: tblPr.addNewTblBorders();
		CTBorder hBorder = borders.isSetInsideH() ? borders.getInsideH()
				: borders.addNewInsideH();
		hBorder.setVal(borderType);
		hBorder.setSz(new BigInteger(size));
		hBorder.setColor(color);
		hBorder.setSpace(new BigInteger(space));
 
		CTBorder vBorder = borders.isSetInsideV() ? borders.getInsideV()
				: borders.addNewInsideV();
		vBorder.setVal(borderType);
		vBorder.setSz(new BigInteger(size));
		vBorder.setColor(color);
		vBorder.setSpace(new BigInteger(space));
 
		CTBorder lBorder = borders.isSetLeft() ? borders.getLeft() : borders
				.addNewLeft();
		lBorder.setVal(borderType);
		lBorder.setSz(new BigInteger(size));
		lBorder.setColor(color);
		lBorder.setSpace(new BigInteger(space));
 
		CTBorder rBorder = borders.isSetRight() ? borders.getRight() : borders
				.addNewRight();
		rBorder.setVal(borderType);
		rBorder.setSz(new BigInteger(size));
		rBorder.setColor(color);
		rBorder.setSpace(new BigInteger(space));
 
		CTBorder tBorder = borders.isSetTop() ? borders.getTop() : borders
				.addNewTop();
		tBorder.setVal(borderType);
		tBorder.setSz(new BigInteger(size));
		tBorder.setColor(color);
		tBorder.setSpace(new BigInteger(space));
 
		CTBorder bBorder = borders.isSetBottom() ? borders.getBottom()
				: borders.addNewBottom();
		bBorder.setVal(borderType);
		bBorder.setSz(new BigInteger(size));
		bBorder.setColor(color);
		bBorder.setSpace(new BigInteger(space));
	}
	
	/**
	 * @Description: 得到Table的CTTblPr,不存在则新建
	 */
	public static CTTblPr getTableCTTblPr(XWPFTable table) {
		CTTbl ttbl = table.getCTTbl();
		CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl
				.getTblPr();
		return tblPr;
	}
	
	/**
	 * @Description: 设置表格总宽度与水平对齐方式
	 */
	public static void setTableWidthAndHAlign(XWPFTable table, String width, STJc.Enum enumValue) {
		CTTblPr tblPr = getTableCTTblPr(table);
		CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr
				.addNewTblW();
		if (enumValue != null) {
			CTJc cTJc = tblPr.addNewJc();
			cTJc.setVal(enumValue);
		}
		tblWidth.setW(new BigInteger(width));
		tblWidth.setType(STTblWidth.DXA);
	}
	
	/**
	 * @Description: 设置单元格Margin
	 */
	public static void setTableCellMargin(XWPFTable table, int top, int left,
			int bottom, int right) {
		table.setCellMargins(top, left, bottom, right);
	}
	
	/**
	 * @Description: 设置表格列宽
	 */
	public static void setTableGridCol(XWPFTable table, int[] colWidths) {
		CTTbl ttbl = table.getCTTbl();
		CTTblGrid tblGrid = ttbl.getTblGrid() != null ? ttbl.getTblGrid()
				: ttbl.addNewTblGrid();
		for (int j = 0, len = colWidths.length; j < len; j++) {
			CTTblGridCol gridCol = tblGrid.addNewGridCol();
			gridCol.setW(new BigInteger(String.valueOf(colWidths[j])));
		}
	}
	
	/**
	 * @Description: 设置行高
	 */
	public static void setRowHeight(XWPFTableRow row, String hight,
			STHeightRule.Enum heigthEnum) {
		CTTrPr trPr = getRowCTTrPr(row);
		CTHeight trHeight;
		if (trPr.getTrHeightList() != null && trPr.getTrHeightList().size() > 0) {
			trHeight = trPr.getTrHeightList().get(0);
		} else {
			trHeight = trPr.addNewTrHeight();
		}
		trHeight.setVal(new BigInteger(hight));
		if (heigthEnum != null) {
			trHeight.setHRule(heigthEnum);
		}
	}
	
	/**
	 * @Description: 设置底纹
	 */
	public static void setCellShdStyle(XWPFTableCell cell, boolean isShd,
			String shdColor, STShd.Enum shdStyle) {
		CTTcPr tcPr = getCellCTTcPr(cell);
		if (isShd) {
			// 设置底纹
			CTShd shd = tcPr.isSetShd() ? tcPr.getShd() : tcPr.addNewShd();
			if (shdStyle != null) {
				shd.setVal(shdStyle);
			}
			if (shdColor != null) {
				shd.setColor(shdColor);
				shd.setFill(shdColor);
			}
		}
	}
	
	/**
	 * @Description: 得到CTTrPr,不存在则新建
	 */
	public static CTTrPr getRowCTTrPr(XWPFTableRow row) {
		CTRow ctRow = row.getCtRow();
		CTTrPr trPr = ctRow.isSetTrPr() ? ctRow.getTrPr() : ctRow.addNewTrPr();
		return trPr;
	}
	
	/**
	 * 
	 * @Description: 得到Cell的CTTcPr,不存在则新建
	 */
	public static CTTcPr getCellCTTcPr(XWPFTableCell cell) {
		CTTc cttc = cell.getCTTc();
		CTTcPr tcPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
		return tcPr;
	}
	
	/**
	 * @Description: 设置段落间距信息,一行=100 一磅=20
	 */
	public static void setParagraphSpacingInfo(XWPFParagraph p, boolean isSpace,
			String before, String after, String beforeLines, String afterLines,
			boolean isLine, String line, STLineSpacingRule.Enum lineValue) {
		CTPPr pPPr = getParagraphCTPPr(p);
		CTSpacing pSpacing = pPPr.getSpacing() != null ? pPPr.getSpacing()
				: pPPr.addNewSpacing();
		if (isSpace) {
			// 段前磅数
			if (before != null) {
				pSpacing.setBefore(new BigInteger(before));
			}
			// 段后磅数
			if (after != null) {
				pSpacing.setAfter(new BigInteger(after));
			}
			// 段前行数
			if (beforeLines != null) {
				pSpacing.setBeforeLines(new BigInteger(beforeLines));
			}
			// 段后行数
			if (afterLines != null) {
				pSpacing.setAfterLines(new BigInteger(afterLines));
			}
		}
		// 间距
		if (isLine) {
			if (line != null) {
				pSpacing.setLine(new BigInteger(line));
			}
			if (lineValue != null) {
				pSpacing.setLineRule(lineValue);
			}
		}
	}
	
	/**
	 * @Description: 得到段落CTPPr
	 */
	public static CTPPr getParagraphCTPPr(XWPFParagraph p) {
		CTPPr pPPr = null;
		if (p.getCTP() != null) {
			if (p.getCTP().getPPr() != null) {
				pPPr = p.getCTP().getPPr();
			} else {
				pPPr = p.getCTP().addNewPPr();
			}
		}
		return pPPr;
	}
	
	/**
	 * @Description: 设置段落对齐
	 */
	public static void setParagraphAlignInfo(XWPFParagraph p,
			ParagraphAlignment pAlign, TextAlignment valign) {
		if (pAlign != null) {
			p.setAlignment(pAlign);
		}
		if (valign != null) {
			p.setVerticalAlignment(valign);
		}
	}
	
	public static XWPFRun getOrAddParagraphFirstRun(XWPFParagraph p, boolean isInsert,
			boolean isNewLine) {
		XWPFRun pRun = null;
		if (isInsert) {
			pRun = p.createRun();
		} else {
			if (p.getRuns() != null && p.getRuns().size() > 0) {
				pRun = p.getRuns().get(0);
			} else {
				pRun = p.createRun();
			}
		}
		if (isNewLine) {
			pRun.addBreak();
		}
		return pRun;
	}
	
	/**
	 * @Description: 得到单元格第一个Paragraph
	 */
	public static XWPFParagraph getCellFirstParagraph(XWPFTableCell cell) {
		XWPFParagraph p;
		if (cell.getParagraphs() != null && cell.getParagraphs().size() > 0) {
			p = cell.getParagraphs().get(0);
		} else {
			p = cell.addParagraph();
		}
		return p;
	}
	
	/**
	 * @Description: 设置列宽和垂直对齐方式
	 */
	public static void setCellWidthAndVAlign(XWPFTableCell cell, String width,
			STTblWidth.Enum typeEnum, STVerticalJc.Enum vAlign) {
		CTTcPr tcPr = getCellCTTcPr(cell);
		CTTblWidth tcw = tcPr.isSetTcW() ? tcPr.getTcW() : tcPr.addNewTcW();
		if (width != null) {
			tcw.setW(new BigInteger(width));
		}
		if (typeEnum != null) {
			tcw.setType(typeEnum);
		}
		if (vAlign != null) {
			CTVerticalJc vJc = tcPr.isSetVAlign() ? tcPr.getVAlign() : tcPr
					.addNewVAlign();
			vJc.setVal(vAlign);
		}
	}
	
	/**
	 * @Description: 设置段落文本样式(高亮与底纹显示效果不同)设置字符间距信息(CTSignedTwipsMeasure)
	 * @param verticalAlign
	 *            : SUPERSCRIPT上标 SUBSCRIPT下标
	 * @param position
	 *            :字符间距位置：>0提升 <0降低=磅值*2 如3磅=6
	 * @param spacingValue
	 *            :字符间距间距 >0加宽 <0紧缩 =磅值*20 如2磅=40
	 * @param indent
	 *            :字符间距缩进 <100 缩
	 */
 
	@SuppressWarnings("deprecation")
	public static void setParagraphRunFontInfo(XWPFParagraph p, XWPFRun pRun,
			String content, String cnFontFamily, String enFontFamily,
			String fontSize, boolean isBlod, boolean isItalic,
			boolean isStrike, boolean isShd, String shdColor,
			STShd.Enum shdStyle, int position, int spacingValue, int indent) {
		CTRPr pRpr = getRunCTRPr(p, pRun);
		if (StringUtils.isNotBlank(content)) {
			if (content.contains("\n")) {
				String[] lines = content.split("\n");
				pRun.setText(lines[0], 0); 
				for (int i = 1; i < lines.length; i++) {
					pRun.addBreak();
					pRun.setText(lines[i]);
				}
			} else {
				pRun.setText(content, 0);
			}
		}
		// 设置字体
		CTFonts fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr
				.addNewRFonts();
		if (StringUtils.isNotBlank(enFontFamily)) {
			fonts.setAscii(enFontFamily);
			fonts.setHAnsi(enFontFamily);
		}
		if (StringUtils.isNotBlank(cnFontFamily)) {
			fonts.setEastAsia(cnFontFamily);
		}
		// 设置字体大小
		CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
		sz.setVal(new BigInteger(fontSize));
 
		CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr
				.addNewSzCs();
		szCs.setVal(new BigInteger(fontSize));
 
		// 设置字体样式
		// 加粗
		if (isBlod) {
			pRun.setBold(isBlod);
		}
		// 倾斜
		if (isItalic) {
			pRun.setItalic(isItalic);
		}
		// 删除线
		if (isStrike) {
			pRun.setStrike(isStrike);
		}
		if (isShd) {
			// 设置底纹
			CTShd shd = pRpr.isSetShd() ? pRpr.getShd() : pRpr.addNewShd();
			if (shdStyle != null) {
				shd.setVal(shdStyle);
			}
			if (shdColor != null) {
				shd.setColor(shdColor);
				shd.setFill(shdColor);
			}
		}
 
		// 设置文本位置
		if (position != 0) {
			pRun.setTextPosition(position);
		}
		if (spacingValue > 0) {
			// 设置字符间距信息
			CTSignedTwipsMeasure ctSTwipsMeasure = pRpr.isSetSpacing() ? pRpr
					.getSpacing() : pRpr.addNewSpacing();
			ctSTwipsMeasure
					.setVal(new BigInteger(String.valueOf(spacingValue)));
		}
		if (indent > 0) {
			CTTextScale paramCTTextScale = pRpr.isSetW() ? pRpr.getW() : pRpr
					.addNewW();
			paramCTTextScale.setVal(indent);
		}
	}
	
	/**
	 * @Description: 跨列合并
	 */
	public static void mergeCellsHorizontal(XWPFTable table, int row, int fromCell,
			int toCell) {
		for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
			XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
			if (cellIndex == fromCell) {
				// The first merged cell is set with RESTART merge value
				getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.RESTART);
			} else {
				// Cells which join (merge) the first one,are set with CONTINUE
				getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.CONTINUE);
			}
		}
	}
	
	/**
	 * @Description: 得到XWPFRun的CTRPr
	 */
	public static CTRPr getRunCTRPr(XWPFParagraph p, XWPFRun pRun) {
		CTRPr pRpr = null;
		if (pRun.getCTR() != null) {
			pRpr = pRun.getCTR().getRPr();
			if (pRpr == null) {
				pRpr = pRun.getCTR().addNewRPr();
			}
		} else {
			pRpr = p.getCTP().addNewR().addNewRPr();
		}
		return pRpr;
	}
	/**
	 * 
	 * @Function: FileCreateWordTableUtil.java
	 * @Description: 创建word文档
	 *
	 * @param: document word文档
	 * @param: savePath 保存地址
	 * @param:@throws Exception
	 * @return：void
	 *
	 * @author: ysf
	 * @date: 2021年1月26日 上午11:41:24 
	 *
	 * Modification History:
	 * Date         Author      Description
	 * 2021年1月26日      ysf         创建word文档
	 */
	public static void saveDocument(XWPFDocument document, String savePath)
			throws Exception {
		FileOutputStream fos = new FileOutputStream(savePath);
		document.write(fos);
		fos.close();
		document.close();
		
	}
}
