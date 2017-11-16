package com.cedricziel.idea.twig.postfix;

import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.jetbrains.twig.TwigTokenTypes;
import com.jetbrains.twig.elements.TwigElementFactory;
import com.jetbrains.twig.elements.TwigElementTypes;
import org.jetbrains.annotations.NotNull;

public class TwigTransPostfixCompletionTemplate extends PostfixTemplate {

    protected TwigTransPostfixCompletionTemplate() {
        super("trans", "Append a trans filter and move the caret back");
    }

    @Override
    public boolean isApplicable(@NotNull PsiElement psiElement, @NotNull Document document, int i) {
        return PlatformPatterns.psiElement(TwigTokenTypes.DOUBLE_QUOTE).accepts(psiElement);
    }

    @Override
    public void expand(@NotNull PsiElement context, @NotNull Editor editor) {
        StringBuilder newText = new StringBuilder();
        PsiElement parent = context.getParent();
        PsiElement child = parent.getFirstChild();

        newText.append(child.getText());
        while(child.getNextSibling() != null) {
            child = child.getNextSibling();

            newText.append(child.getText());

            if (previousElementIsDoubleQuoteOrString(child) && PlatformPatterns.psiElement(TwigTokenTypes.DOUBLE_QUOTE).accepts(child)) {
                newText.append(" | trans");
            }

        }

        PsiElement psiElement = TwigElementFactory.createPsiElement(context.getProject(), newText.toString(), TwigElementTypes.PRINT_BLOCK);
        if (psiElement != null) {
            PsiElement replacement = context.getParent().replace(psiElement);
            editor.getCaretModel().moveToOffset(replacement.getStartOffsetInParent() + 4);
        }
    }

    private boolean previousElementIsDoubleQuoteOrString(PsiElement child) {
        return PlatformPatterns.psiElement(TwigTokenTypes.STRING_TEXT).accepts(child.getPrevSibling()) || PlatformPatterns.psiElement(TwigTokenTypes.DOUBLE_QUOTE).accepts(child.getPrevSibling());
    }
}
