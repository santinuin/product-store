package com.besysoft.product_store.service.implementations;

import com.besysoft.product_store.domain.Seller;
import com.besysoft.product_store.exception.IdNotFoundException;
import com.besysoft.product_store.exception.NameAlreadyExistsException;
import com.besysoft.product_store.repository.SellerRepository;
import com.besysoft.product_store.service.interfaces.SellerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository repository;

    public SellerServiceImpl(SellerRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Seller> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Seller findById(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Seller findByName(String name) {
        return this.repository.findByNameContainingIgnoreCase(name).orElseThrow();
    }

    @Override
    @Transactional
    public Seller create(Seller seller) throws NameAlreadyExistsException {

        Optional<Seller> sellerByName = this.repository.findByNameIgnoreCase(seller.getName());

        if(sellerByName.isPresent()){
            throw new NameAlreadyExistsException("Error: no se pudo crear, el nombre: "
                    .concat(seller.getName().concat(" ya existe.")));
        }

        return this.repository.save(seller);
    }

    @Override
    @Transactional
    public Seller update(Long id, Seller seller) throws IdNotFoundException, NameAlreadyExistsException {

        Optional<Seller> sellerToUpdate = this.repository.findById(id);

        if (sellerToUpdate.isEmpty()){
            throw new IdNotFoundException("Error: no se pudo editar, el vendedor ID: "
                    .concat(id.toString().concat(" no existe.")));
        }

        Optional<Seller> sellerByName = this.repository.findByNameIgnoreCase(seller.getName());

        if (sellerByName.isPresent() && !Objects.equals(sellerByName.get().getName(), sellerToUpdate.get().getName())){
            throw new NameAlreadyExistsException("Error: no se pudo editar, el nombre "
                    .concat(seller.getName().concat(" ya existe.")));
        }

        sellerToUpdate.get().setName(seller.getName());
        sellerToUpdate.get().setSalary(seller.getSalary());

        return this.repository.save(sellerToUpdate.get());
    }

    @Override
    @Transactional
    public void delete(Long id) throws IdNotFoundException {

        Optional<Seller> sellerToDelete = this.repository.findById(id);

        if (sellerToDelete.isEmpty()){
            throw new IdNotFoundException("Error: no se pudo eliminar, el vendedor ID: "
                    .concat(id.toString().concat(" no existe.")));
        }

        this.repository.deleteById(id);

    }
}
