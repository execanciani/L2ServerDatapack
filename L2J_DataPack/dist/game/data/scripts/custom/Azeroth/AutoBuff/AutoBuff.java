/*
 * Copyright (C) 2004-2015 L2J DataPack
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
package custom.Azeroth.AutoBuff;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.datatables.SkillData;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.events.EventType;
import com.l2jserver.gameserver.model.events.ListenerRegisterType;
import com.l2jserver.gameserver.model.events.annotations.RegisterEvent;
import com.l2jserver.gameserver.model.events.annotations.RegisterType;
import com.l2jserver.gameserver.model.events.impl.character.OnCreatureZoneEnter;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.model.zone.type.L2TownZone;

/**
 * @author Eze
 */
public class AutoBuff extends AbstractNpcAI
{
	private final Skill[] _mageskills =
	{
		SkillData.getInstance().getSkill(1035, 1),
		SkillData.getInstance().getSkill(1040, 1),
		SkillData.getInstance().getSkill(1045, 1),
		SkillData.getInstance().getSkill(1048, 1),
		SkillData.getInstance().getSkill(1059, 1),
		SkillData.getInstance().getSkill(1062, 1),
		SkillData.getInstance().getSkill(1078, 1),
		SkillData.getInstance().getSkill(1085, 1),
		SkillData.getInstance().getSkill(1204, 1),
		SkillData.getInstance().getSkill(1240, 1),
		SkillData.getInstance().getSkill(1397, 1),
		SkillData.getInstance().getSkill(264, 1),
		SkillData.getInstance().getSkill(266, 1),
		SkillData.getInstance().getSkill(268, 1),
		SkillData.getInstance().getSkill(273, 1),
		SkillData.getInstance().getSkill(276, 1),
		SkillData.getInstance().getSkill(304, 1)
	};
	
	private final Skill[] _fighterkills =
	{
		SkillData.getInstance().getSkill(1035, 1),
		SkillData.getInstance().getSkill(1036, 1),
		SkillData.getInstance().getSkill(1040, 1),
		SkillData.getInstance().getSkill(1045, 1),
		SkillData.getInstance().getSkill(1048, 1),
		SkillData.getInstance().getSkill(1062, 1),
		SkillData.getInstance().getSkill(1068, 1),
		SkillData.getInstance().getSkill(1077, 1),
		SkillData.getInstance().getSkill(1086, 1),
		SkillData.getInstance().getSkill(1204, 1),
		SkillData.getInstance().getSkill(1240, 1),
		SkillData.getInstance().getSkill(1242, 1),
		SkillData.getInstance().getSkill(1397, 1),
		SkillData.getInstance().getSkill(264, 1),
		SkillData.getInstance().getSkill(266, 1),
		SkillData.getInstance().getSkill(268, 1),
		SkillData.getInstance().getSkill(269, 1),
		SkillData.getInstance().getSkill(271, 1),
		SkillData.getInstance().getSkill(273, 1),
		SkillData.getInstance().getSkill(274, 1),
		SkillData.getInstance().getSkill(275, 1),
		SkillData.getInstance().getSkill(304, 1),
		SkillData.getInstance().getSkill(310, 1)
	};
	
	public AutoBuff()
	{
		super(AutoBuff.class.getSimpleName(), "custom/Azeroth");
		
	}
	
	@RegisterEvent(EventType.ON_CREATURE_ZONE_ENTER)
	@RegisterType(ListenerRegisterType.GLOBAL)
	public void autoBuffZone(OnCreatureZoneEnter event)
	{
		L2PcInstance player = null;
		if (event.getCreature() instanceof L2PcInstance)
		{
			player = (L2PcInstance) event.getCreature();
		}
		
		if (player != null)
		{
			if (event.getZone().getClass().equals(L2TownZone.class))
			{
				if (player.isMageClass())
				{
					for (Skill skill : _mageskills)
					{
						skill.applyEffects(player, player);
					}
				}
				else
				{
					for (Skill skill : _fighterkills)
					{
						skill.applyEffects(player, player);
					}
				}
				player.sendMessage("Sientes el poder de los Dioses.");
			}
		}
		
	}
	
	public static void main(String[] args)
	{
		new AutoBuff();
	}
}
