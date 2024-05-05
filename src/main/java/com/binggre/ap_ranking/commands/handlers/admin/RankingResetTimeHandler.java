package com.binggre.ap_ranking.commands.handlers.admin;

import com.binggre.ap_ranking.config.ApRankingConfig;
import com.binggre.ap_ranking.objects.Day;
import com.binggre.binggreapi.command.CommandHandler;
import com.binggre.binggreapi.command.SimpleCommandExecutor;
import com.binggre.binggreapi.utils.NumberUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RankingResetTimeHandler extends CommandHandler {

    public RankingResetTimeHandler(SimpleCommandExecutor executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(super.getDescription());
            return false;
        }
        Day day = Day.findByName(args[1]);
        int time = NumberUtil.parseInt(args[2]);

        if (day == null) {
            sender.sendMessage("§c존재하지 않는 요일입니다.");
            return false;
        }
        if (time == NumberUtil.PARSE_ERROR) {
            sender.sendMessage("§c숫자를 입력해 주세요.");
            return false;
        }
        ApRankingConfig config = ApRankingConfig.getInst();
        config.setResetDay(day);
        config.setResetTime(time);
        config.save();
        sender.sendMessage(String.format("초기화 주기가 %s요일 %s시 로 설정되었습니다.", day.getName(), time));
        return true;
    }
}