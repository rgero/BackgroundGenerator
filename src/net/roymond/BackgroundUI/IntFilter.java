package net.roymond.BackgroundUI;

import javax.swing.text.*;

/**
 * The Integer Filter I've used a lot.
 * Created by Roymond on 2/16/2017.
 */
class IntFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string,
                             AttributeSet attr) throws BadLocationException {

        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.insert(offset, string);

        if (stringTest(sb.toString())) {
            super.insertString(fb, offset, string, attr);
        }
    }

    private boolean stringTest(String text) {
        if (text.equals("")){
            return true;
        }
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
                        AttributeSet attrs) throws BadLocationException {

        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.replace(offset, offset + length, text);

        if (stringTest(sb.toString())) {
            super.replace(fb, offset, length, text, attrs);
        }

    }

    @Override
    public void remove(FilterBypass fb, int offset, int length)
            throws BadLocationException {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.delete(offset, offset + length);

        if (stringTest(sb.toString())) {
            super.remove(fb, offset, length);
        }

    }
}