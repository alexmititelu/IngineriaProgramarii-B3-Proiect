package ro.uaic.info.ip.proiect.b3.clientinfo;

public class LinieContinutFisier {
    private String lineValue = null;
    private String comment = null;
    private Integer commentedLines = null;

    public Integer getCommentedLines() {
        return commentedLines;
    }

    public void setCommentedLines(Integer commentedLines) {
        this.commentedLines = commentedLines;
    }

    public String getLineValue() {
        return lineValue;
    }

    public void setLineValue(String lineValue) {
        this.lineValue = lineValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
