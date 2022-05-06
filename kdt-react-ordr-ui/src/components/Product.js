import React from 'react';

export function Product(props) {
    const productId = props.productId;
    const prodcutName = props.prodcutName;
    const category = props.category;
    const price = props.price;

    const handleAddButtonClick = e => {
        props.onAddClick(productId);
    };

    return (
        <>
            <div className="col-2">
                <img className="img-fluid" src="https://i.imgur.com/HKOFQYa.jpeg" alt="" />
            </div>
            <div className="col">
                <div className="row text-muted">{category}</div>
                <div className="row">{prodcutName}</div>
            </div>
            <div className="col text-center price">{price}원</div>
            <div className="col text-end action">
                <button onClick={handleAddButtonClick} className="btn btn-small btn-outline-dark" href="">추가</button>
            </div>
        </>
    );
}
