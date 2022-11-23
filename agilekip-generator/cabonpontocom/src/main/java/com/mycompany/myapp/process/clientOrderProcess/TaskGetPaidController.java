package com.mycompany.myapp.process.clientOrderProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client-order-process/task-get-paid")
public class TaskGetPaidController {

    private final Logger log = LoggerFactory.getLogger(TaskGetPaidController.class);

    private final TaskGetPaidService taskGetPaidService;

    public TaskGetPaidController(TaskGetPaidService taskGetPaidService) {
        this.taskGetPaidService = taskGetPaidService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskGetPaidContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskGetPaidContextDTO taskGetPaidContext = taskGetPaidService.loadContext(id);
        return ResponseEntity.ok(taskGetPaidContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskGetPaidContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskGetPaidContextDTO taskGetPaidContext = taskGetPaidService.claim(id);
        return ResponseEntity.ok(taskGetPaidContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskGetPaidContextDTO taskGetPaidContext) {
        log.debug("REST request to complete ClientOrderProcess.TaskGetPaid {}", taskGetPaidContext.getTaskInstance().getId());
        taskGetPaidService.complete(taskGetPaidContext);
        return ResponseEntity.noContent().build();
    }
}
