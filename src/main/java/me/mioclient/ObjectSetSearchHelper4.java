package me.mioclient;

import baritone.api.BaritoneAPI;
import baritone.api.behavior.IPathingBehavior;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.process.IBaritoneProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.api.selection.ISelection;
import baritone.api.utils.interfaces.IGoalRenderPos;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ObjectSetSearchHelper4.class */
public final class ObjectSetSearchHelper4 implements SearchHelper_4, ObstaclePasserHelper {
    public final ObjectSet<me.mioclient.module.Feature> objectSet = ObjectSets.synchronize(new ObjectOpenHashSet());

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/ObjectSetSearchHelper4$BaritoneProcess.class */
    private class BaritoneProcess implements IBaritoneProcess {
        public BaritoneProcess() {
        }

        public boolean isActive() {
            return !ObjectSetSearchHelper4.this.objectSet.isEmpty();
        }

        public PathingCommand onTick(boolean z, boolean z2) {
            return new PathingCommand((Goal) null, PathingCommandType.REQUEST_PAUSE);
        }

        public boolean isTemporary() {
            return true;
        }

        public void onLostControl() {
        }

        public String displayName0() {
            return "Mio pause service";
        }

        public double priority() {
            return 999.0;
        }
    }

    public ObjectSetSearchHelper4() {
        baritoneHelper.do1796(this);
        BaritoneAPI.getProvider().getPrimaryBaritone().getPathingControlManager().registerProcess(new BaritoneProcess());
        BaritoneAPI.getSettings().useMessageTag.value = false;
        BaritoneAPI.getSettings().logger.value = text -> {
            Text copy = text.copy();
            ((MutableText) copy).getSiblings().add(0, Text.literal(" "));
            ((MutableText) copy).getSiblings().add(0, MixinMessageIndicatorHelper.getText342());
            minecraftClient.inGameHud.getChatHud().addMessage(copy, (MessageSignatureData) null, MixinMessageIndicatorHelper.messageIndicator);
        };
    }

    @Override // me.mioclient.ObstaclePasserHelper
    public void do699(me.mioclient.module.Feature feature) {
        if (this.objectSet.contains(feature)) {
            return;
        }
        this.objectSet.add(feature);
    }

    @Override // me.mioclient.ObstaclePasserHelper
    public void do700(me.mioclient.module.Feature feature) {
        if (this.objectSet.contains(feature)) {
            this.objectSet.remove(feature);
        }
    }

    @Override // me.mioclient.ObstaclePasserHelper
    public boolean is701() {
        return !this.objectSet.isEmpty();
    }

    @Override // me.mioclient.ObstaclePasserHelper
    public boolean is702() {
        return BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing();
    }

    @Override // me.mioclient.ObstaclePasserHelper
    public void do703(double d) {
        BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(GoalXZ.fromDirection(minecraftClient.player.getPos(), minecraftClient.player.getYaw(), 5.0));
    }

    @Override // me.mioclient.ObstaclePasserHelper
    public void do704(BlockPos blockPos) {
        BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalBlock(blockPos));
    }

    @Override // me.mioclient.ObstaclePasserHelper
    public void do705(BlockPos blockPos) {
        BaritoneAPI.getProvider().getPrimaryBaritone().getElytraProcess().pathTo(blockPos);
    }

    @Override // me.mioclient.ObstaclePasserHelper
    public void do707(BlockPos blockPos) {
        BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoal(new GoalBlock(blockPos));
    }

    @Override // me.mioclient.ObstaclePasserHelper
    public void do706() {
        IPathingBehavior pathingBehavior = BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior();
        pathingBehavior.cancelEverything();
        pathingBehavior.forceCancel();
    }

    @Override // me.mioclient.ObstaclePasserHelper
    public boolean is708(BlockPos blockPos) {
        ISelection lastSelection = BaritoneAPI.getProvider().getPrimaryBaritone().getSelectionManager().getLastSelection();
        if (lastSelection == null) {
            return false;
        }
        return Box.enclosing(lastSelection.min(), lastSelection.max()).contains(blockPos.toCenterPos());
    }

    @Override // me.mioclient.ObstaclePasserHelper
    public BlockPos getBlockPos710() {
        IGoalRenderPos goal = BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().getGoal();
        if (goal instanceof IGoalRenderPos) {
            return goal.getGoalPos();
        }
        if (!(goal instanceof GoalXZ)) {
            return null;
        }
        GoalXZ goalXZ = (GoalXZ) goal;
        return BlockPos.ofFloored(goalXZ.getX(), minecraftClient.player.getY(), goalXZ.getZ());
    }

    @Override // me.mioclient.ObstaclePasserHelper
    public boolean is709() {
        return true;
    }
}
