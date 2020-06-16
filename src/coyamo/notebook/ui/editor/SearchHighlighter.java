package coyamo.notebook.ui.editor;

import java.awt.Color;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;

public class SearchHighlighter extends DefaultHighlighter {

	private BaseEditor editor;
	private DefaultHighlightPainter painter;
	private SearchingListener listener;
	private Highlight currentHighlight;

	public Highlight getCurrentHighlight() {
		return currentHighlight;
	}

	public void setListener(SearchingListener listener) {
		this.listener = listener;
	}

	public void highlightSelection() {
		try {
			if (editor.hasSelectText())
				addHighlight(editor.getSelectionStart(), editor.getSelectionEnd(),
						new DefaultHighlightPainter(editor.getSelectionColor()));
		} catch (BadLocationException e) {
		}
	}

	public SearchHighlighter(BaseEditor editor) {
		super();
		this.editor = editor;
		painter = new DefaultHighlightPainter(new Color(255, 152, 0, 100));
	}

	int pos = 0;
	int length = 0;
	String patteren;
	String text = "";

	public void setSearchWords(String patteren) {
		this.patteren = patteren;
		refreshFromEditor();

	}

	public void refreshFromEditor() {
		try {
			currentHighlight = null;
			Document doc = editor.getDocument();
			this.pos = editor.getCaretPosition() < 0 ? 0 : editor.getCaretPosition();
			this.text = editor.getText(0, doc.getLength());
			this.length = text.length();
		} catch (Exception e) {
		}
	}

	public boolean findNext() {

		try {
			removeAllHighlights();
			currentHighlight = null;
			if (pos == length) {
				if (listener != null)
					listener.onResult(RESULT.NOT_FOUND, -1, -1);
				pos = 0;
			}
			int index = text.toUpperCase().indexOf(patteren.toUpperCase(), pos);
			if (index >= 0) {
				currentHighlight = (Highlight) addHighlight(index, index + patteren.length(), painter);
				pos = index + patteren.length();
				editor.setCaretPosition(pos);

				if (listener != null)
					listener.onResult(RESULT.FOUND, index, index + patteren.length());
				return true;
			} else {
				if (listener != null)
					listener.onResult(RESULT.NOT_FOUND, -1, -1);
				pos = 0;
				return false;
			}
		} catch (Exception e) {
			if (listener != null)
				listener.onResult(RESULT.ERROR, -1, -1);
		}
		return false;
	}

	public interface SearchingListener {
		void onResult(RESULT result, int start, int end);
	}

	public enum RESULT {
		FOUND, NOT_FOUND, ERROR
	}

}
