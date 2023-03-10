package org.rygn.kanban.utils;

import java.time.LocalDate;
import java.time.Month;

import org.rygn.kanban.dao.DeveloperRepository;
import org.rygn.kanban.dao.TaskRepository;
import org.rygn.kanban.dao.TaskStatusRepository;
import org.rygn.kanban.dao.TaskTypeRepository;
import org.rygn.kanban.domain.Developer;
import org.rygn.kanban.domain.Task;
import org.rygn.kanban.domain.TaskStatus;
import org.rygn.kanban.domain.TaskType;
import org.rygn.kanban.service.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadDatabase {

	@Bean
	@Profile("!test")
	CommandLineRunner initDatabase(DeveloperRepository developerRepository,
									TaskRepository taskRepository,
									TaskStatusRepository taskStatusRepository,
									TaskTypeRepository taskTypeRepository,
									TaskService taskService) {
		
		return args -> {			
			initTaskStatusAndTypes(taskStatusRepository, taskTypeRepository);
			
			initDevelopers(developerRepository);	
		};
	}
	
	private void initDevelopers(DeveloperRepository developerRepository) {
		
		Developer devRG = new Developer();
		devRG.setEmail("remy.girodon@gmail.com");
		devRG.setFirstname("Rémy");
		devRG.setLastname("Girodon");
		devRG.setPassword("abc123");
		devRG.setStartContract(LocalDate.of(2017, Month.NOVEMBER, 1));
		developerRepository.save(devRG);
		
		Developer devFXC = new Developer();
		devFXC.setEmail("francois-xavier.cote@gmail.com");
		devFXC.setFirstname("François-Xavier");
		devFXC.setLastname("Cote");
		devFXC.setPassword("abc123");
		devFXC.setStartContract(LocalDate.of(2019, Month.SEPTEMBER, 1));
		developerRepository.save(devFXC);
		
		Developer devPJD = new Developer();
		devPJD.setEmail("pierre-jean.drevet@gmail.com");
		devPJD.setFirstname("Pierre-Jean");
		devPJD.setLastname("Drevet");
		devPJD.setPassword("abc123");
		devPJD.setStartContract(LocalDate.of(2020, Month.JANUARY, 1));
		developerRepository.save(devPJD);
		
	}
	
	private void initTaskStatusAndTypes(TaskStatusRepository taskStatusRepository,
										TaskTypeRepository taskTypeRepository) {
		
		TaskStatus todo = new TaskStatus(Constants.TASK_STATUS_TODO_ID, Constants.TASK_STATUS_TODO_LABEL);
		taskStatusRepository.save(todo);
		
		TaskStatus doing = new TaskStatus(Constants.TASK_STATUS_DOING_ID, Constants.TASK_STATUS_DOING_LABEL);
		taskStatusRepository.save(doing);
		
		TaskStatus test = new TaskStatus(Constants.TASK_STATUS_TEST_ID, Constants.TASK_STATUS_TEST_LABEL);
		taskStatusRepository.save(test);
		
		TaskStatus done = new TaskStatus(Constants.TASK_STATUS_DONE_ID, Constants.TASK_STATUS_DONE_LABEL);
		taskStatusRepository.save(done);
		
		TaskType feature = new TaskType(Constants.TASK_TYPE_FEATURE_ID, Constants.TASK_TYPE_FEATURE_LABEL);
		taskTypeRepository.save(feature);
		
		TaskType bug = new TaskType(Constants.TASK_TYPE_BUG_ID, Constants.TASK_TYPE_BUG_LABEL);
		taskTypeRepository.save(bug);
	}
	
	@Bean
	@Profile("test")
	CommandLineRunner initTestDatabase(DeveloperRepository developerRepository,
									TaskRepository taskRepository,
									TaskStatusRepository taskStatusRepository,
									TaskTypeRepository taskTypeRepository) {
		
		return args -> {			
			initTaskStatusAndTypes(taskStatusRepository, taskTypeRepository);
			
			Developer dev1 = new Developer();
			dev1.setEmail("dev1@dev.dev");
			dev1.setFirstname("dev1");
			dev1.setLastname("dev1");
			dev1.setPassword("dev1");
			dev1.setStartContract(LocalDate.of(2017, Month.NOVEMBER, 1));
			developerRepository.save(dev1);
			
			Task task1 = new Task();
			task1.setCreated(LocalDate.now());
			task1.setTitle("task1");
			task1.setNbHoursForecast(0);
			task1.setNbHoursReal(0);
			task1.addDeveloper(dev1);
			task1.setType(taskTypeRepository.findById(Constants.TASK_TYPE_FEATURE_ID).orElse(null));
			task1.setStatus(taskStatusRepository.findById(Constants.TASK_STATUS_TODO_ID).orElse(null));
			taskRepository.save(task1);
			
		};
	}
}
