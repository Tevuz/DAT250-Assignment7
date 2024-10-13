package no.hvl.dat250.h600871.expass7.controller;

import no.hvl.dat250.h600871.expass7.controller.data.VoteOptionDTO;
import no.hvl.dat250.h600871.expass7.model.VoteOption;
import no.hvl.dat250.h600871.expass7.service.Exceptions.VoteOptionException;
import no.hvl.dat250.h600871.expass7.service.VoteOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/voteOption/")
@CrossOrigin
public class VoteOptionController {

    private VoteOptionService service;

    public VoteOptionController(@Autowired VoteOptionService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<VoteOptionDTO> createVoteOption(@RequestBody VoteOption voteOption) {
        return ResponseEntity.created(URI.create("/"+voteOption.getId())).body(VoteOptionDTO.from(service.createVoteOption(voteOption)));
    }

    @GetMapping("")
    public ResponseEntity<List<VoteOptionDTO>> getVoteOptions() {
        return ResponseEntity.ok(service.readAllVoteOptions().stream().map(VoteOptionDTO::from).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<VoteOptionDTO> getVoteOptionById(@PathVariable long id) {
        Optional<VoteOption> voteOption = service.readVoteOptionById(id);
        if (voteOption.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(VoteOptionDTO.from(voteOption.get()));
    }

    @PutMapping("")
    public ResponseEntity<VoteOptionDTO> updateVoteOption(@RequestBody VoteOptionDTO voteOption) {
        // TODO: commit votes to repository
        return ResponseEntity.ok(VoteOptionDTO.from(service.updateVoteOption(voteOption.into())));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteVoteOption(@PathVariable long id) {
        try {
            service.deleteVoteOptionById(id);
            return ResponseEntity.noContent().build();
        } catch (VoteOptionException exception) {
            return ResponseEntity.notFound().build();
        }
    }


}
