package com.work.ppm.commons.utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class TemplateStructureCheckUtil {

    public static class CheckResult {
        private final boolean pass;
        private final List<String> errors;

        public CheckResult(boolean pass, List<String> errors) {
            this.pass = pass;
            this.errors = errors;
        }

        public boolean isPass() { return pass; }
        public List<String> getErrors() { return errors; }
    }

    public static class DocStructure {
        public final List<List<String>> rows;

        public DocStructure(List<List<String>> rows) {
            this.rows = rows;
        }
    }

    /**
     * 校验上传文件是否符合模板结构（只校验骨架，不校验数据）
     */
    public CheckResult check(MultipartFile uploaded, Path templatePath) {
        List<String> errors = new ArrayList<>();
        try {
            List<DocStructure> tplStruct;
            try (InputStream is = Files.newInputStream(templatePath)) {
                tplStruct = extractStructure(is);
            }
            List<DocStructure> uplStruct = extractStructure(uploaded.getInputStream());

            if (tplStruct.size() != uplStruct.size()) {
                errors.add("表格数量与模板不一致：模板" + tplStruct.size()
                        + "个表格，上传文件" + uplStruct.size() + "个表格");
                return new CheckResult(false, errors);
            }

            for (int t = 0; t < tplStruct.size(); t++) {
                checkTable(t + 1, tplStruct.get(t), uplStruct.get(t), errors);
            }
        } catch (IOException e) {
            errors.add("文件解析失败，请确认是有效的 .docx 文件：" + e.getMessage());
        }
        return new CheckResult(errors.isEmpty(), errors);
    }

    private void checkTable(int tableNo, DocStructure tpl, DocStructure upl, List<String> errors) {
        int tplRows = tpl.rows.size();
        int uplRows = upl.rows.size();

        if (uplRows < tplRows) {
            errors.add("第" + tableNo + "个表格行数不足：模板" + tplRows + "行，上传文件仅" + uplRows + "行");
        }

        int common = Math.min(tplRows, uplRows);
        for (int r = 0; r < common; r++) {
            List<String> tplRow = tpl.rows.get(r);
            List<String> uplRow = upl.rows.get(r);

            for (int c = 0; c < tplRow.size(); c++) {
                String tplCell = tplRow.get(c);
                if (tplCell.isEmpty()) continue;   // 模板空白 = 数据区，跳过

                String uplCell = (c < uplRow.size()) ? uplRow.get(c) : "";
                if (isFillableCell(tplCell)) {
                    // ── 可填充：只校验标签结构 ──
                    if (!checkLabelsPresent(tplCell, uplCell)) {
                        errors.add(String.format(
                                "第%d个表格 第%d行 第%d列 标签缺失或乱序：模板为【%s】，实际为【%s】",
                                tableNo, r + 1, c + 1, tplCell, uplCell));
                    }
                } else {
                    // ── 固定内容：精确匹配 ──
                    if (!tplCell.equals(uplCell)) {
                        errors.add(String.format(
                                "第%d个表格 第%d行 第%d列 内容不符：模板为【%s】，实际为【%s】",
                                tableNo, r + 1, c + 1, tplCell, uplCell));
                    }
                }
            }
        }
    }

    private List<DocStructure> extractStructure(InputStream is) throws IOException {
        List<DocStructure> tables = new ArrayList<>();
        try (XWPFDocument doc = new XWPFDocument(is)) {
            for (XWPFTable table : doc.getTables()) {
                List<List<String>> rows = new ArrayList<>();
                for (XWPFTableRow row : table.getRows()) {
                    List<String> cells = new ArrayList<>();
                    for (XWPFTableCell cell : row.getTableCells()) {
                        cells.add(normalize(cell.getText()));
                    }
                    rows.add(cells);
                }
                tables.add(new DocStructure(rows));
            }
        }
        return tables;
    }

    private String normalize(String text) {
        return text == null ? "" : text.replaceAll("\\s+", "");
    }

    /**
     * 判断是否为"可填充"单元格：包含中文/英文冒号即视为标签区
     */
    private boolean isFillableCell(String cell) {
        return cell.contains("：") || cell.contains(":");
    }

    /**
     * 校验上传内容中是否包含模板的全部标签，且顺序一致。
     *
     * 原理：把模板按冒号切出标签段，逐个在上传内容中做"有序查找"。
     * 例：模板  "约谈原因：约谈结果：被约谈人签字：负责人签字：日期："
     *     实际  "约谈原因：测试111约谈结果：测试222被约谈人签字：谭磊负责人签字：陈伟日期：20260903"
     *     → 依次找到 约谈原因 → 约谈结果 → 被约谈人签字 → 负责人签字 → 日期  ✓
     */
    private boolean checkLabelsPresent(String tplCell, String uplCell) {
        // 按中英文冒号切分，取非空段作为标签
        String[] parts = tplCell.split("[：:]");
        int searchPos = 0;  // 在 uplCell 中的搜索起点，保证顺序
        for (String label : parts) {
            String trimmed = label.trim();
            if (trimmed.isEmpty()) continue;

            int idx = uplCell.indexOf(trimmed, searchPos);
            if (idx < 0) {
                // 标签缺失 或 顺序被打乱
                return false;
            }
            searchPos = idx + trimmed.length();
        }
        return true;
    }
}