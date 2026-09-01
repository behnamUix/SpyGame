package com.behnamuix.spygame

import com.behnamuix.spygame.core.media.controller.MusicController
import com.behnamuix.spygame.core.media.presentation.viewmodel.MusicPlayerViewModel
import com.behnamuix.spygame.data.local.db.repository.keyword.KeywordRepository
import com.behnamuix.spygame.feature.configgame.domain.usecase.ConfigGameUseCase
import com.behnamuix.spygame.feature.configgame.presentation.viewmodel.ConfigGameViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ConfigGameViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val keywordRepo = mock<KeywordRepository>()
    private val mediaVm = mock<MusicController>()
    private val useCase = mock<ConfigGameUseCase>()

    private lateinit var viewModel: ConfigGameViewModel

    @Before
    fun setup(){
        //az in dispacher estefade kon
        Dispatchers.setMain(dispatcher)
        viewModel = ConfigGameViewModel(
            keywordRepo,
            mediaVm,
            useCase
        )
    }
    //bad az test
    @After
    fun tearDown() {
        //dispacher ro bargardon be ghabl
        Dispatchers.resetMain()
    }
    @Test
    fun `inc agent count should update agent count`() = runTest {

        whenever(useCase.incAgentCountPlayer())
            .thenReturn(2)

        viewModel.incAgentCountPlayer()

        advanceUntilIdle()

        assertEquals(2, viewModel.agentCount.value)
    }
}