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
package custom.Azeroth.EventoCofre;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.datatables.NpcData;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.templates.L2NpcTemplate;
import com.l2jserver.util.Rnd;

/**
 * @author Eze
 */
public class EventoCofre extends AbstractNpcAI
{
	public EventoCofre()
	{
		super(EventoCofre.class.getSimpleName(), "custom");
		
		startQuestTimer("cofrespawn", Rnd.get(1, 120 * 60000), null, null);
		startQuestTimer("llavespawn", Rnd.get(1, 120 * 60000), null, null);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		L2NpcTemplate cofre = NpcData.getInstance().getTemplate(560);
		
		if (event.equalsIgnoreCase("cofrespawn"))
		{
			addSpawn(560, pos)
			
		}
		if (event.equalsIgnoreCase("llavespawn"))
		{
			
		}
		return super.onAdvEvent(event, npc, player);
	}
}
