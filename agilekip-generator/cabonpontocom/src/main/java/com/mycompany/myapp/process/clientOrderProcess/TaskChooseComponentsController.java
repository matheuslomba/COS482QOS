package com.mycompany.myapp.process.clientOrderProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client-order-process/task-choose-components")
public class TaskChooseComponentsController {

    private final Logger log = LoggerFactory.getLogger(TaskChooseComponentsController.class);

    private final TaskChooseComponentsService taskChooseComponentsService;

    public TaskChooseComponentsController(TaskChooseComponentsService taskChooseComponentsService) {
        this.taskChooseComponentsService = taskChooseComponentsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskChooseComponentsContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskChooseComponentsContextDTO taskChooseComponentsContext = taskChooseComponentsService.loadContext(id);
        return ResponseEntity.ok(taskChooseComponentsContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskChooseComponentsContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskChooseComponentsContextDTO taskChooseComponentsContext = taskChooseComponentsService.claim(id);
        return ResponseEntity.ok(taskChooseComponentsContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskChooseComponentsContextDTO taskChooseComponentsContext) {
        log.debug(
            "REST request to complete ClientOrderProcess.TaskChooseComponents {}",
            taskChooseComponentsContext.getTaskInstance().getId()
        );
        taskChooseComponentsService.complete(taskChooseComponentsContext);
        return ResponseEntity.noContent().build();
    }
}
