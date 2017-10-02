package network.wego.cucumber.stepdefs;

import network.wego.WegoApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = WegoApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
