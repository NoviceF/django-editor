package org.kacprzak.eclipse.django_editor.editors.completion;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.swt.graphics.Image;
import org.kacprzak.eclipse.django_editor.DjangoPlugin;
import org.kacprzak.eclipse.django_editor.IDjangoImages;
import org.kacprzak.eclipse.django_editor.templates.DjangoContextType;
import org.kacprzak.eclipse.django_editor.templates.TemplateManager;

/**
 * Django tags completion processor
 * @author Zbigniew Kacprzak
*/
public class DjangoTagCompletionProcessor extends TemplateCompletionProcessor
{

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[]{ '{'};
	}

	@Override
	protected TemplateContextType getContextType(ITextViewer viewer, IRegion region) {
		String id = DjangoContextType.DJANGO_CONTEXT_TYPE_TAG;
		return TemplateManager.getDjangoContextTypeRegistry().getContextType(id);
	}

	@Override
	protected Image getImage(Template template) {
		ImageRegistry registry = DjangoPlugin.getDefault().getImageRegistry();
		Image image = registry.get(IDjangoImages.TAG_IMAGE);
		return image;
	}

	@Override
	protected Template[] getTemplates(String contextTypeId) {
		return TemplateManager.getDjangoTemplateStore().getTemplates();
	}

	@Override
	protected String extractPrefix(ITextViewer viewer, int offset) {
		int i= offset;
		IDocument document= viewer.getDocument();
		if (i > document.getLength())
			return ""; //$NON-NLS-1$

		try {
//			while (i > 0) {
//				char ch= document.getChar(i - 1);
//				if (!Character.isJavaIdentifierPart(ch) && ch != '{' )
//					break;
//				i--;
//			}
			char ch = document.getChar(i - 1);
			// if we are just after '{', replace it with selected tag
			if (ch == '{')
				return document.get(i-1, 1);

			return document.get(i, offset - i);
		} catch (BadLocationException e) {
			return ""; //$NON-NLS-1$
		}
	}
}
