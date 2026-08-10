package com.behnamuix.spygame

import com.behnamuix.spygame.feature.configgame.data.local.ConfigGameDataSource
import com.behnamuix.spygame.feature.configgame.data.repository.ConfigGameRepositoryImpl
import com.behnamuix.spygame.feature.configgame.domain.model.Agent
import com.behnamuix.spygame.feature.configgame.domain.model.Spy
import com.behnamuix.spygame.feature.configgame.domain.repository.ConfigGameRepository
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class ConfigGameRepositoryImplTest {
   private lateinit var repository: ConfigGameRepositoryImpl
   @Before
   fun setup(){

       val dataSource= ConfigGameDataSource()
       repository= ConfigGameRepositoryImpl(dataSource)
   }
    @Test
    fun `inc agent count`(){
        Agent.count=1
        val result=repository.incAgentCountPlayer()
        assertEquals(2,result)
    }
    @Test
    fun `dec agent count`(){
        Agent.count=2
        val result=repository.decAgentCountPlayer()
        assertEquals(1,result)
    }
    @Test
    fun `inc spy count`(){
        Spy.count=1
        val result=repository.incSpyCountPlayer()
        assertEquals(2,result)

    }
    @Test
    fun `dec spy count`(){
        Spy.count=2
        val result=repository.decSpyCountPlayer()
        assertEquals(1,result)
    }

}