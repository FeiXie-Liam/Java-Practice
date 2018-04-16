package com.tw.command;

import com.tw.AchievementSystem;
import com.tw.command.Command;

public class ExitCommand implements Command {
    private AchievementSystem achievementSystem;

    public ExitCommand(AchievementSystem achievementSystem) {
        this.achievementSystem = achievementSystem;
    }

    @Override
    public void invoke() {
        achievementSystem.exit();
    }
}
