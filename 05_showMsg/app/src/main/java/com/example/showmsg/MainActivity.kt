package com.example.showmsg

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.showmsg.ui.theme.ShowMsgTheme
import kotlinx.coroutines.launch

/*
 * what this project do:
 * Toast and snack bar in Compose, use timer to refresh state and show in snackbar
 * logic:
 * 1. click button -> Toast
 * 2. click button -> snack bar
 * 3. change state to trigger snack bar
 * 4. start timer in view model to trigger snack bar
 * 5. refresh text in compose from view model
 * timer in View Model -> snack bar
 * new technics:
 * scaffold
 * View Model in Compose
 * new technic points:
 * implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1'
 * implementation 'androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1'
 * */
class MainActivity : ComponentActivity() {
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        viewModel.liveDataChanged.observe(this as LifecycleOwner, Observer {
//            Log.v("sssssssssss","liveDataChanged3:${viewModel.liveDataChanged.value}")
//            viewModel.viewModelScope.launch {
//                viewModel.state = true
//                Log.v("viewModelScope","state3:${viewModel.state}")
//            }
//        })
        setContent {
            ShowMsgTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    val owner = LocalViewModelStoreOwner.current
//                    owner?.let {
//                        val mainViewModel: MainViewModel = viewModel(it,"MainViewModel",MainViewModelFactory(this.application))
//                        MainActivityContent(mainViewModel)
//                    }
                    //Toast && Snackbar
                    MainActivityContent(mainViewModel)
                    //Snackbar
                    //MainActivityContentOfScaffold()
                }
            }
        }
    }
}

@Composable
fun MainActivityContent(viewModel: MainViewModel) {
    var context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    //success
    var refreshCount by remember { mutableStateOf(1) }
    //success
    var bState by remember { mutableStateOf<Boolean>(false) }
    //failuere
    var bState2 = remember { mutableStateOf<Boolean>(false) }.value
    //failure
    val liveData = viewModel.liveDataChanged.observeAsState()
    //failure
    var liveData2 by remember { mutableStateOf<Boolean>(viewModel.state1) }
    //failure
    val liveData3 by rememberSaveable{ mutableStateOf<Boolean>(viewModel.state1) }
    Column() {
        Button(onClick = {
            Toast.makeText(context,"this is toast",Toast.LENGTH_LONG).show()
        }) {
            Text(text = "Show Toast")
        }
        Button(onClick = {
            scope.launch {
                snackbarHostState.showSnackbar(
                    "I am Snackbar"
                )
            }
        }) {
            Text(text = "Show Snackbar")
        }
        Button(onClick = {
            refreshCount++
        }) {
            Text(text = "change state to trigger Snackbar")
        }
        Button(onClick = {
            viewModel.start = viewModel.start.not()
        }) {
            Text(text = "start timer in view model to trigger Snackbar")
        }
        Text(
            text = viewModel.text
        )
    }
    //separate layout
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){
        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackbarHostState
        )
    }
    LaunchedEffect(key1 = refreshCount){
        scope.launch {
            snackbarHostState.showSnackbar(
                "Snackbar #${refreshCount}"
            )
        }
    }
    LaunchedEffect(key1 = viewModel.state1){
        scope.launch {
            Log.v("sssssssssss","viewModel.state:${viewModel.state1}")
            snackbarHostState.showSnackbar(
                "Snackbar #${viewModel.state1}"
            )
        }
    }
}

@Composable
fun MainActivityContentOfScaffold() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            var clickCount by remember { mutableStateOf(0) }
            ExtendedFloatingActionButton(
                onClick = {
                    // show snackbar as a suspend function
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Snackbar # ${++clickCount}"
                        )
                    }
                }
            ) { Text("Show snackbar") }
        },
        content = { innerPadding ->
            Text(
                text = "Body content",
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .wrapContentSize()
            )
        }
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShowMsgTheme {
        Greeting("Android")
    }
}