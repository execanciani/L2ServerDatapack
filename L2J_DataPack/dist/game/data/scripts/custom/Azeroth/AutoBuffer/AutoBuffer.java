/*
 * Copyright (C) 2004-2013 L2J DataPack
 *
 * This file is part of L2J DataPack.
 *
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package custom.Azeroth.AutoBuffer;

/**
 * @autor: fissban
 */

import java.util.Collection;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.ClassId;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.skills.Skill;

public class AutoBuffer extends AbstractNpcAI
{
	// NPC
	private static final int Newbie_Helper = 1000004;
	// Spawn state
	private static boolean SPAWNED = false;
	
	static final Location[] SPAWNS =
	{
		// Sacado de la DB
		new Location(17039, 144772, -3000, 14750),
	};
	
	protected AutoBuffer(String name, String descr)
	{
		super(name, descr);
		
		addStartNpc(Newbie_Helper);
		addSpawnId(Newbie_Helper);
		addTalkId(Newbie_Helper);
		
		if (!SPAWNED)
		{
			for (Location spawn : SPAWNS)
			{
				addSpawn(Newbie_Helper, spawn, false, 0);
			}
			SPAWNED = true;
		}
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		ThreadPoolManager.getInstance().scheduleGeneral(new BufferAI(npc), 5000);
		
		return super.onSpawn(npc);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		return "32327.htm";
	}
	
	protected class BufferAI implements Runnable
	{
		private final L2Npc _npc;
		
		// Skills
		private final SkillHolder HASTE_1 = new SkillHolder(4327, 1);
		private final SkillHolder HASTE_2 = new SkillHolder(5632, 1);
		private final SkillHolder CUBIC = new SkillHolder(4338, 1);
		private final SkillHolder[] FIGHTER_BUFFS =
		{
			new SkillHolder(4322, 1), // Wind Walk
			new SkillHolder(4323, 1), // Shield
			new SkillHolder(5637, 1), // Magic Barrier
			new SkillHolder(4324, 1), // Bless the Body
			new SkillHolder(4325, 1), // Vampiric Rage
			new SkillHolder(4326, 1), // Regeneration
		};
		private final SkillHolder[] MAGE_BUFFS =
		{
			new SkillHolder(4322, 1), // Wind Walk
			new SkillHolder(4323, 1), // Shield
			new SkillHolder(5637, 1), // Magic Barrier
			new SkillHolder(4328, 1), // Bless the Soul
			new SkillHolder(4329, 1), // Acumen
			new SkillHolder(4330, 1), // Concentration
			new SkillHolder(4331, 1), // Empower
		};
		private final SkillHolder[] SUMMON_BUFFS =
		{
			new SkillHolder(4322, 1), // Wind Walk
			new SkillHolder(4323, 1), // Shield
			new SkillHolder(5637, 1), // Magic Barrier
			new SkillHolder(4324, 1), // Bless the Body
			new SkillHolder(4325, 1), // Vampiric Rage
			new SkillHolder(4326, 1), // Regeneration
			new SkillHolder(4328, 1), // Bless the Soul
			new SkillHolder(4329, 1), // Acumen
			new SkillHolder(4330, 1), // Concentration
			new SkillHolder(4331, 1), // Empower
		};
		
		protected BufferAI(L2Npc caster)
		{
			_npc = caster;
		}
		
		@Override
		public void run()
		{
			
			if ((_npc == null) || !_npc.isVisible())
			{
				return;
			}
			
			Collection<L2PcInstance> plrs = _npc.getKnownList().getKnownPlayers().values();
			for (L2PcInstance player : plrs)
			{
				if ((player == null) || player.isInvul() || player.isDead() || (player.getLevel() > 75) || (player.getLevel() < 6) || player.isCursedWeaponEquipped() || (player.getKarma() != 0) || !_npc.isInsideRadius(player, 500, false, false))
				{
					continue;
				}
				// summons
				if ((player.getSummon() != null) && player.getSummon().isServitor())
				{
					for (SkillHolder skills : SUMMON_BUFFS)
					{
						CastSummon(player.getSummon(), skills.getSkill());
					}
					if (player.getLevel() > 40)
					{
						CastSummon(player.getSummon(), HASTE_2.getSkill());
					}
					else
					{
						CastSummon(player.getSummon(), HASTE_1.getSkill());
					}
				}
				// magos
				if (player.isMageClass() && (player.getClassId() != ClassId.overlord) && (player.getClassId() != ClassId.warcryer))
				{
					for (SkillHolder skills : MAGE_BUFFS)
					{
						CastPlayer(player, skills.getSkill());
					}
				}
				// warrios
				else
				{
					for (SkillHolder skills : FIGHTER_BUFFS)
					{
						CastPlayer(player, skills.getSkill());
					}
					if (player.getLevel() > 40)
					{
						CastPlayer(player, HASTE_2.getSkill());
					}
					else
					{
						CastPlayer(player, HASTE_1.getSkill());
					}
				}
				if ((player.getLevel() >= 16) && (player.getLevel() <= 34))
				{
					player.doSimultaneousCast(CUBIC.getSkill());
				}
			}
			ThreadPoolManager.getInstance().scheduleGeneral(this, 3000);
		}
		
		// metodo para los players
		private boolean CastPlayer(L2PcInstance player, Skill skill)
		{
			if (player.getEffectList().getBuffCount() < 7)
			{
				skill.applyEffects(_npc, player);
				return true;
			}
			// SkillData.getInstance().getSkill(skill.getId(), skill.getLevel()).applyEffects(player, player);
			
			return false;
		}
		
		// metodo para los summons
		private boolean CastSummon(L2Summon summon, Skill skill)
		{
			if (summon.getEffectList().getBuffCount() < 10)
			{
				skill.applyEffects(_npc, summon);
				// SkillData.getInstance().getSkill(skill.getId(), skill.getLevel()).applyEffects(summon, summon);
				return true;
			}
			return false;
		}
	}
	
	public static void main(String[] args)
	{
		new AutoBuffer(AutoBuffer.class.getSimpleName(), "ai/npc");
	}
	
}