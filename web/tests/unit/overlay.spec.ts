import Overlay from '@/components/Overlay.vue';
import { mount } from '@vue/test-utils';
import { expect } from 'chai';

describe('Overlay has elements', () => {
    it('Component has div with id overlay', () => {
        const wrapper = mount(Overlay);

        expect(wrapper.contains('div')).to.equal(true);
        expect(wrapper.contains('#overlay')).to.equal(true);
    });

    it('Overlay has close button', () => {
        const wrapper = mount(Overlay);

        expect(wrapper.contains('div button')).to.equal(true);
        expect(wrapper.find('button').text()).to.equal('x');
    });
});
