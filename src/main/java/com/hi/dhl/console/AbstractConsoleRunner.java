package com.hi.dhl.console;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ConsoleTitleGen;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.execution.ui.RunContentManager;
import com.intellij.execution.ui.actions.CloseAction;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class AbstractConsoleRunner<T extends ConsoleView> {

    private final String myConsoleTitle;
    private ProcessHandler myProcessHandler;
    private final String myWorkingDir;
    private T myConsoleView;
    @NotNull
    private final Project myProject;

    public AbstractConsoleRunner(@NotNull Project project, @NotNull String consoleTitle, @Nullable String workingDir) {
        myProject = project;
        myConsoleTitle = consoleTitle;
        myWorkingDir = workingDir;
    }

    /**
     * Launch process, setup history, actions etc.
     */
    public void initAndRun() throws ExecutionException {
        // Create Server process
        final Process process = createProcess();
        UIUtil.invokeLaterIfNeeded(() -> {
            // Init console view
            myConsoleView = createConsoleView();

            myProcessHandler = createProcessHandler(process);

            ProcessTerminatedListener.attach(myProcessHandler);

            // Attach to process
            myConsoleView.attachToProcess(myProcessHandler);

            // Runner creating
            createContentDescriptorAndActions();

            // Run
            myProcessHandler.startNotify();
        });
    }

    protected Executor getExecutor() {
        return DefaultRunExecutor.getRunExecutorInstance();
    }

    protected void createContentDescriptorAndActions() {
        final Executor defaultExecutor = getExecutor();
        final DefaultActionGroup toolbarActions = new DefaultActionGroup();
        final ActionToolbar actionToolbar = ActionManager.getInstance().createActionToolbar("ConsoleRunner", toolbarActions, false);

        // Runner creating
        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(actionToolbar.getComponent(), BorderLayout.WEST);
        panel.add(myConsoleView.getComponent(), BorderLayout.CENTER);

        actionToolbar.setTargetComponent(panel);

        final RunContentDescriptor contentDescriptor =
                new RunContentDescriptor(myConsoleView, myProcessHandler, panel, constructConsoleTitle(myConsoleTitle), getConsoleIcon());

        contentDescriptor.setAutoFocusContent(isAutoFocusContent());

        // toolbar actions
        final List<AnAction> actions = fillToolBarActions(toolbarActions, defaultExecutor, contentDescriptor);
        registerActionShortcuts(actions, panel);

        showConsole(defaultExecutor, contentDescriptor);
    }

    @Nullable
    protected Icon getConsoleIcon() {
        return null;
    }

    protected String constructConsoleTitle(final @NotNull String consoleTitle) {
        return new ConsoleTitleGen(myProject, consoleTitle, shouldAddNumberToTitle()).makeTitle();
    }

    public boolean isAutoFocusContent() {
        return true;
    }

    protected boolean shouldAddNumberToTitle() {
        return false;
    }

    protected void showConsole(Executor defaultExecutor, @NotNull RunContentDescriptor contentDescriptor) {
        // Show in run tool window
        RunContentManager.getInstance(myProject).showRunContent(defaultExecutor, contentDescriptor);
    }

    protected abstract T createConsoleView();

    @Nullable
    protected abstract Process createProcess() throws ExecutionException;

    protected abstract OSProcessHandler createProcessHandler(final Process process);

    protected abstract List<AnAction> fillToolBarActions(final DefaultActionGroup toolbarActions,
                                                         final Executor defaultExecutor,
                                                         final RunContentDescriptor contentDescriptor);

    public static void registerActionShortcuts(final List<? extends AnAction> actions, final JComponent component) {
        for (AnAction action : actions) {
            action.registerCustomShortcutSet(action.getShortcutSet(), component);
        }
    }

    protected AnAction createCloseAction(final Executor defaultExecutor, final RunContentDescriptor myDescriptor) {
        return new CloseAction(defaultExecutor, myDescriptor, myProject);
    }

    @NotNull
    public Project getProject() {
        return myProject;
    }

    public String getConsoleTitle() {
        return myConsoleTitle;
    }

    public String getWorkingDir() {
        return myWorkingDir;
    }

    public ProcessHandler getProcessHandler() {
        return myProcessHandler;
    }
}
