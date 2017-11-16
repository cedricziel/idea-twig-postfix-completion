package com.cedricziel.idea.twig.postfix;

import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateProvider;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class TwigPostfixCompletionTemplateProvider implements PostfixTemplateProvider {
    @NotNull
    @Override
    public Set<PostfixTemplate> getTemplates() {
        Set<PostfixTemplate> templates = new HashSet<PostfixTemplate>();
        templates.add(new TwigTransPostfixCompletionTemplate());

        return templates;
    }

    @Override
    public boolean isTerminalSymbol(char c) {
        return '.' == c;
    }

    @Override
    public void preExpand(@NotNull PsiFile psiFile, @NotNull Editor editor) {

    }

    @Override
    public void afterExpand(@NotNull PsiFile psiFile, @NotNull Editor editor) {

    }

    @NotNull
    @Override
    public PsiFile preCheck(@NotNull PsiFile psiFile, @NotNull Editor editor, int i) {
        return psiFile;
    }
}
